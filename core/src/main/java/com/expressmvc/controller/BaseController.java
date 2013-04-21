package com.expressmvc.controller;

import com.expressmvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BaseController {
    ModelAndView service(HttpServletRequest req, HttpServletResponse resp);
}
