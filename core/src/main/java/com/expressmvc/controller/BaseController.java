package com.expressmvc.controller;

import com.expressmvc.ModelAndView;
import com.expressmvc.view.VelocityViewRender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public abstract class BaseController {
    public void service(HttpServletRequest req, HttpServletResponse resp) {

        ModelAndView mv = doService(req, resp);

        if (!mv.hasView()) {
//            mv.(getConventionalViewFor(req));
        }

        render(mv, req, resp);

        try {
            resp.getWriter().write("this is a fake rending message. remove me.");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private String getConventionalViewFor(HttpServletRequest req) {
        return ""; //TODO
    }

    private void render(ModelAndView mv, HttpServletRequest req, HttpServletResponse resp) {
        VelocityViewRender velocityView = new VelocityViewRender();
        velocityView.setViewName(mv.getViewTemplateName());

        velocityView.render(mv, req, resp);
    }

    public abstract ModelAndView doService(HttpServletRequest req, HttpServletResponse resp);
}
