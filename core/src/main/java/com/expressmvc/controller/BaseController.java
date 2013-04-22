package com.expressmvc.controller;

import com.expressmvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseController {
    public void service(HttpServletRequest req, HttpServletResponse resp) {

        ModelAndView mv = doService(req, resp);

        if (!mv.hasView()) {
            mv.setViewName(getConventionalViewFor(req));
        }

        render(mv, req, resp);
        //render modelAndView
    }

    private String getConventionalViewFor(HttpServletRequest req) {
        return ""; //TODO
    }

    private void render(ModelAndView mv, HttpServletRequest req, HttpServletResponse resp) {

    }

    public abstract ModelAndView doService(HttpServletRequest req, HttpServletResponse resp);
}
