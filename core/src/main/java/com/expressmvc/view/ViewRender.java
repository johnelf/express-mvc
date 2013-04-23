package com.expressmvc.view;

import com.expressmvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ViewRender {
    public void render(ModelAndView model, HttpServletRequest request, HttpServletResponse response);
}
