package com.expressmvc.controller;

import com.expressmvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AppFormController<T> extends BaseController {

    @Override
    public ModelAndView doService(HttpServletRequest req, HttpServletResponse resp) {
        T requestModel = bindRequestParametersToModel(req);
        Envelope envelope = handleFormSubmit(new Envelope(req, resp), requestModel);
        return createModelAndViewBasedOn(envelope);
    }

    private ModelAndView createModelAndViewBasedOn(Envelope envelope) {
        return null;
    }

    private T bindRequestParametersToModel(HttpServletRequest req) {
        return null;
    }

    public abstract Envelope handleFormSubmit(Envelope envelope, T requestModel);
}
