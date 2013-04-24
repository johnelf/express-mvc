package com.expressmvc.controller;

import com.expressmvc.ModelAndView;
import com.expressmvc.view.ViewRender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseController {
    private ViewRender viewRender;

    public void service(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView mv = doService(req, resp);
        render(mv, req, resp);
    }

    private void render(ModelAndView mv, HttpServletRequest req, HttpServletResponse resp) {
        viewRender.render(mv, req, resp);
    }

    public abstract ModelAndView doService(HttpServletRequest req, HttpServletResponse resp);

    public void setViewRender(ViewRender viewRender) {
        this.viewRender = viewRender;
    }
}
