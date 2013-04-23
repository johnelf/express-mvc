package com.expressmvc.servlet;

import com.expressioc.Container;
import com.expressioc.ExpressContainer;
import com.expressmvc.controller.BaseController;
import com.expressmvc.controller.MappingResolver;
import com.expressmvc.view.VelocityConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatchServlet extends HttpServlet {
    private Container servletContainer = new ExpressContainer("com.expressmvc");
    private MappingResolver mappingResolver;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mappingResolver.init(config);
        VelocityConfig.init(getServletContext().getContextPath());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = req.getRequestURI();
        BaseController controller = getControllerFor(requestURI);
        if (controller == null) {
            try {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {}
            return;
        }

        controller.service(req, resp);

//        VelocityViewRender velocityView = new VelocityViewRender();
//        velocityView.setUrl("hello.vm");
//        Map<String, Object> model = newHashMap();
//        User siteUser = new BIConversion.User("john");
//        Map<String, Object> model = new HashMap<String, Object>();
//        Article siteUser = new BIConversion.Article("john");
//        model.put("user", siteUser);
//        velocityView.render(model, req, resp);
    }

    private BaseController getControllerFor(String requestURI) {
        BaseController controller = null;
        Class<? extends BaseController> controllerClazz = mappingResolver.getControllerFor(requestURI);
        if (controllerClazz != null) {
            controller = servletContainer.getComponent(controllerClazz);
        }

        return controller;
    }

    public void setMappingResolver(MappingResolver mappingResolver) {
        this.mappingResolver = mappingResolver;
    }
}
