package com.expressmvc.controller;

import com.expressioc.utility.ClassUtility;
import com.expressmvc.ModelAndView;
import com.expressmvc.annotation.Path;
import com.expressmvc.model.DataBinder;
import com.expressmvc.model.ModelContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

public class AppController {
    private DataBinder dataBinder;
    private Object delegate;

    public AppController(Object delegate) {
        this.delegate = delegate;
    }

    public ModelAndView doService(HttpServletRequest req, HttpServletResponse resp) {

        Method handlerMethod = getHandlerMethodInController(req);
        if (handlerMethod == null) {
            return new ModelAndView();
        }

        return handleRequestBy(handlerMethod, req, resp);
    }

    private ModelAndView handleRequestBy(Method handlerMethod, HttpServletRequest req, HttpServletResponse resp) {
        ModelContainer modelContainer = null;

        Object[] params = assembleParametersFor(handlerMethod, req, resp);

        try {
            if (handlerMethod.getReturnType().equals(Void.TYPE)) {
                handlerMethod.invoke(delegate, params);
                modelContainer = new ModelContainer(params);
            } else {
                modelContainer = (ModelContainer) handlerMethod.invoke(delegate, params);
                modelContainer = modelContainer == null ? new ModelContainer(params) : modelContainer.add(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return createModelAndView(modelContainer, handlerMethod.getName());
    }

    private Method getHandlerMethodInController(HttpServletRequest req) {
        Method methodForRequest = null;
        String requestMethod = req.getMethod();
        String path = req.getServletPath();

        if ("GET".equals(requestMethod)) {
            methodForRequest = findMethodWith(path);
        } else if ("POST".equals(requestMethod)) {
            methodForRequest = findMethodWith(path);
        }

        return methodForRequest;
    }

    private Method findMethodWith(String path) {
        Method[] methods = delegate.getClass().getMethods();
        Path clazzRoute = delegate.getClass().getAnnotation(Path.class);
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

    private ModelAndView createModelAndView(ModelContainer modelContainer, String handlerMethodName) {
        ModelAndView mv = new ModelAndView(handlerMethodName.toLowerCase());
        return putViewIngredientsIntoMV(modelContainer, mv);
    }

    private ModelAndView putViewIngredientsIntoMV(ModelContainer modelContainer, ModelAndView mv) {
        if (modelContainer.getContents() != null) {
            for (Map.Entry<String, Object> o : modelContainer.getContents().entrySet()) {
                mv.addModel(o.getKey(), o.getValue());
            }
        }

        return mv;
    }

    public void setDataBinder(DataBinder dataBinder) {
        this.dataBinder = dataBinder;
    }
}
