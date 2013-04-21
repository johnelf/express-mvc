package com.expressmvc.servlet;

import com.expressmvc.controller.AnnotationBasedMappingResolver;
import com.expressmvc.controller.MappingResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatchServlet extends HttpServlet {
    private MappingResolver mappingResolver = new AnnotationBasedMappingResolver();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mappingResolver.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.getWriter().write("hello$");
        } catch (IOException e) {
        }
    }
}
