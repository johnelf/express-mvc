package com.expressmvc.controller;

import com.expressioc.utility.ClassUtility;
import com.expressmvc.ModelAndView;
import com.expressmvc.annotation.Path;
import com.expressmvc.model.DataBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;

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
        ModelAndView mv = new ModelAndView();
        Object[] params = assembleParametersFor(handlerMethod, req, resp);

        try {
            if (handlerMethod.getReturnType().equals(Void.TYPE)) {
                handlerMethod.invoke(delegate, params);
                mv = new ModelAndView(params);
            } else {
                Object handleResult = handlerMethod.invoke(delegate, params);
                if (!(handleResult instanceof ModelAndView)) {
                    mv.add(handleResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mv.setViewName(handlerMethod.getName());
    }

    private Method getHandlerMethodInController(HttpServletRequest req) {
        Method[] methods = delegate.getClass().getMethods();
        String clazzRoute = delegate.getClass().getAnnotation(Path.class).value();

        for (Method method : methods) {
            if (methodCanHandleRequest(req, clazzRoute, method)) {
                return method;
            }
        }

        return null;
    }

    private boolean methodCanHandleRequest(HttpServletRequest req, String clazzRoute, Method method) {
        if (!method.isAnnotationPresent(Path.class)) {
            return false;
        }

        return req.getServletPath().equals(clazzRoute + method.getAnnotation(Path.class).value());
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

    public void setDataBinder(DataBinder dataBinder) {
        this.dataBinder = dataBinder;
    }
}
