package com.expressmvc.controller;

import com.expressmvc.ModelAndView;
import com.expressmvc.annotation.ViewIngredient;
import com.expressmvc.annotation.http.GET;
import com.expressmvc.annotation.http.POST;
import com.expressmvc.binder.DataBinder;
import com.expressmvc.exception.DataBindException;
import com.expressmvc.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import static com.google.common.collect.Lists.newArrayList;

public class AppController extends BaseController {
    private DataBinder dataBinder;

    @Override
    public ModelAndView doService(HttpServletRequest req, HttpServletResponse resp) {
        Envelope envelope = null;

        String requestMethod = req.getMethod();
        Method methodForRequest = null;

        if ("GET".equals(requestMethod)) {
            methodForRequest = findMethodWith(GET.class); //TODO multi GET Path annotations support
            if (methodForRequest == null) {
                return new ModelAndView(req);
            }
        } else if ("POST".equals(requestMethod)){
            methodForRequest = findMethodWith(POST.class);
        }

        Object[] parameters = assembleParametersFor(methodForRequest, req);

        try {
            envelope = (Envelope) methodForRequest.invoke(this, parameters); //TODO force cast? without result method?
        } catch (IllegalAccessException e) {                                 //TODO
        } catch (InvocationTargetException e) {
        }

        return createModelAndViewBasedOn(envelope, req);
    }

    private Object[] assembleParametersFor(Method httpGetHandler, HttpServletRequest request) {
        ArrayList<Object> params = newArrayList();

        Class<?>[] parameterTypes = httpGetHandler.getParameterTypes();
        for (Class paramClazz : parameterTypes) {
            //TODO check know type, such as HttpServletRequest, and HttpServletResponse

            Object param = ClassUtils.newInstanceOf(paramClazz); //TODO using ioc to initialize object
            try {
                dataBinder.bind(request, param);
            } catch (DataBindException e) {
                //TODO
            }

            params.add(param);
        }

        return params.toArray(new Object[params.size()]); //TODO test zero parameter
    }

    private Method findMethodWith(Class<? extends Annotation> annotationClass) {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClass)) {
                return method;
            }
        }

        return null;
    }

    private ModelAndView createModelAndViewBasedOn(Envelope envelope, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(request);
        return putViewIngredientsIntoMV(envelope, mv);
    }

    private ModelAndView putViewIngredientsIntoMV(Envelope envelope, ModelAndView mv) {
        Iterator contentsIterator = envelope.getContentsIterator();

        while (contentsIterator.hasNext()) {
            Object o = contentsIterator.next();
            Class<?> clazz = o.getClass();

            boolean isViewIngredients = clazz.isAnnotationPresent(ViewIngredient.class);
            if (isViewIngredients) {
                String ingredientName = clazz.getAnnotation(ViewIngredient.class).value();
                mv.addViewIngredient(ingredientName, o);
            }
        }

        return mv;
    }

    public void setDataBinder(DataBinder dataBinder) {
        this.dataBinder = dataBinder;
    }
}
