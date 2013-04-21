package com.expressmvc.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatchServlet extends HttpServlet {
    public static final String CONTROLLER_SCAN_PATH = "controller-scan-path";
    private String scanPath;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        scanPath = config.getInitParameter(CONTROLLER_SCAN_PATH);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.getWriter().write("hello$");
            resp.getWriter().write(scanPath);
        } catch (IOException e) {
        }
    }
}
