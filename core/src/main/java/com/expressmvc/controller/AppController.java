package com.expressmvc.controller;

import com.expressioc.utility.ClassUtility;
import com.expressmvc.ModelAndView;
import com.expressmvc.annotation.Path;
import com.expressmvc.annotation.ViewIngredient;
import com.expressmvc.annotation.http.GET;
import com.expressmvc.annotation.http.POST;
import com.expressmvc.model.DataBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;

public class AppController {
    private DataBinder dataBinder;

    public ModelAndView doService(HttpServletRequest req, HttpServletResponse resp) {

        Method handlerMethod = getHandlerMethodInController(req);
        if (handlerMethod == null) {
            return new ModelAndView();
        }

        return handleRequestBy(handlerMethod, req, resp);
    }

    private ModelAndView handleRequestBy(Method handlerMethod, HttpServletRequest req, HttpServletResponse resp) {
        Envelope envelope = null;

        Object[] params = assembleParametersFor(handlerMethod, req, resp);

        try {
            if (handlerMethod.getReturnType().equals(Void.TYPE)) {
                handlerMethod.invoke(this, params);
                envelope = new Envelope(req, params);
            } else {
                envelope = (Envelope) handlerMethod.invoke(this, params);
                envelope = envelope == null ? new Envelope(req, params) : envelope.add(req);
            }
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }

        return createModelAndView(envelope, handlerMethod.getName());
    }

    private Method getHandlerMethodInController(HttpServletRequest req) {
        Method methodForRequest = null;
        String requestMethod = req.getMethod();
        String path = req.getServletPath();

        if ("GET".equals(requestMethod)) {
            methodForRequest = findMethodWith(GET.class, path);
        } else if ("POST".equals(requestMethod)) {
            methodForRequest = findMethodWith(POST.class, path);
        }

        return methodForRequest;
    }

    private Method findMethodWith(Class<? extends Annotation> annotationClass, String path) {
        Method[] methods = this.getClass().getMethods();
        Path clazzRoute = this.getClass().getAnnotation(Path.class);
        if (clazzRoute != null) {
            for (Method method : methods) {
                if (method.isAnnotationPresent(Path.class)) {
                    String methodRoute = method.getAnnotation(Path.class).value();
                    if (path.equals(clazzRoute.value() + methodRoute)
                            || (path.equals("/") && methodRoute.equals("/"))) {
                        return method;
                    }
                }
            }
        }

        return null;
    }

    private Object[] assembleParametersFor(Method handlerMethod, HttpServletRequest request, HttpServletResponse response) {
        ArrayList<Object> params = newArrayList();

        Class<?>[] parameterTypes = handlerMethod.getParameterTypes();
        for (Class paramClazz : parameterTypes) {

            if (HttpServletRequest.class.equals(paramClazz)) {
                params.add(request);
                continue;
            }

            if (HttpServletResponse.class.equals(paramClazz)) {
                params.add(response);
                continue;
            }

            Object param = ClassUtility.newInstanceOf(paramClazz);

            dataBinder.bind(request, param);

            params.add(param);
        }

        return params.toArray(new Object[params.size()]);
    }

    private ModelAndView createModelAndView(Envelope envelope, String handlerMethodName) {
        ModelAndView mv = new ModelAndView(handlerMethodName.toLowerCase());
        return putViewIngredientsIntoMV(envelope, mv);
    }

    private ModelAndView putViewIngredientsIntoMV(Envelope envelope, ModelAndView mv) {
        for (Object o : envelope.getContents().values()) {

            boolean isViewIngredient = o.getClass().isAnnotationPresent(ViewIngredient.class);
            if (isViewIngredient) {
                String ingredientName = o.getClass().getAnnotation(ViewIngredient.class).value();
                mv.addViewIngredient(ingredientName, o);
            }
        }

        return mv;
    }

    public void setDataBinder(DataBinder dataBinder) {
        this.dataBinder = dataBinder;
    }
}
