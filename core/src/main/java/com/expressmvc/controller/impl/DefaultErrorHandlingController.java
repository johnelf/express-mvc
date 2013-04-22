package com.expressmvc.controller.impl;

import com.expressmvc.ModelAndView;
import com.expressmvc.controller.ErrorHandlerController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultErrorHandlingController extends ErrorHandlerController {
    @Override
    public ModelAndView service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.getWriter().write("hello from DefaultErrorHandlingController");
        } catch (IOException e) {
            e.printStackTrace();//TODO
        }
        return null; //TODO
    }
}
