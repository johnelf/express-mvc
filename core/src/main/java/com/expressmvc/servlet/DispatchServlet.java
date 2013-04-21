package com.expressmvc.servlet;

import com.expressioc.Container;
import com.expressioc.ExpressContainer;
import com.expressmvc.controller.BaseController;
import com.expressmvc.controller.ErrorHandlerController;
import com.expressmvc.controller.MappingResolver;
import com.expressmvc.view.VelocityConfig;
import com.expressmvc.view.VelocityView;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class DispatchServlet extends HttpServlet {
    private Container servletContainer = new ExpressContainer();

    private MappingResolver mappingResolver;
    private ErrorHandlerController defaultErrorHandleController;

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
        controller.service(req, resp);

        VelocityView velocityView = new VelocityView();
        velocityView.setUrl("hello.vm");
        Map<String, Object> model = new HashMap<String, Object>();
        User siteUser = new User("john");
        model.put("user", siteUser);
        velocityView.render(model, req, resp);
    }

    private BaseController getControllerFor(String requestURI) {
        BaseController controller = null;
        Class<? extends BaseController> controllerClazz = mappingResolver.getControllerFor(requestURI);
        if (controllerClazz != null) {
            controller = servletContainer.getComponent(controllerClazz);
        }

        if (controller == null) {
            return defaultErrorHandleController;
        }

        return controller;
    }

    public void setDefaultErrorHandleController(ErrorHandlerController defaultErrorHandleController) {
        this.defaultErrorHandleController = defaultErrorHandleController;
    }

    public void setMappingResolver(MappingResolver mappingResolver) {
        this.mappingResolver = mappingResolver;
    }
}
