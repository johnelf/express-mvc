package com.expressmvc.controller;

import com.expressmvc.ModelAndView;
import com.expressmvc.annotation.ViewIngredient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

public abstract class AppFormController<T> extends BaseController {

    @Override
    public ModelAndView doService(HttpServletRequest req, HttpServletResponse resp) {
        T requestModel = bindRequestParametersToModel(req);
        Envelope envelope = handleFormSubmit(new Envelope(req, resp), requestModel);
        return createModelAndViewBasedOn(envelope);
    }

    private ModelAndView createModelAndViewBasedOn(Envelope envelope) {
        ModelAndView mv = new ModelAndView();
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

    private T bindRequestParametersToModel(HttpServletRequest req) {
        return null;
    }

    public abstract Envelope handleFormSubmit(Envelope envelope, T requestModel);
}
