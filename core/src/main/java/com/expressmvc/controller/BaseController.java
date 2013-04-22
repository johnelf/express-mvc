package com.expressmvc.controller;

import com.expressmvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseController {
    public void service(HttpServletRequest req, HttpServletResponse resp) {
        //TODO1
        ModelAndView modelAndView = doService(req, resp);
        //render modelAndView
    }

    public abstract ModelAndView doService(HttpServletRequest req, HttpServletResponse resp);
}
