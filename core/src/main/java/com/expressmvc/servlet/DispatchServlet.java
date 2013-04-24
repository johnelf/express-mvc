package com.expressmvc.servlet;

import com.expressioc.Container;
import com.expressioc.ExpressContainer;
import com.expressmvc.controller.BaseController;
import com.expressmvc.controller.MappingResolver;
import com.expressmvc.view.VelocityConfig;
import com.google.common.base.Strings;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatchServlet extends HttpServlet {
    public static final String MODEL_INSTANCE_CREATOR = "modelInstanceCreator";  //TODO move

    private Container controllersContainer;
    private Container modelInstanceCreator;
    private MappingResolver mappingResolver;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mappingResolver.init(config);
        controllersContainer = initAppControllerContainer(config);
        modelInstanceCreator = initAppControllerContainer(config);       //TODO init by append '/models'
        VelocityConfig.init(getServletContext().getContextPath());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        BaseController controller = getControllerFor(req.getRequestURI());
        if (controller == null) {
            try {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {}
            return;
        }

        controller.setModelInstanceCreator(modelInstanceCreator);
        controller.service(req, resp);
    }

    public void setMappingResolver(MappingResolver mappingResolver) {
        this.mappingResolver = mappingResolver;
    }

    private BaseController getControllerFor(String requestURI) {
        BaseController controller = null;

        Class<? extends BaseController> controllerClazz = mappingResolver.getControllerFor(requestURI);
        if (controllerClazz != null) {
            controller = controllersContainer.getComponent(controllerClazz);
        }

        return controller;
    }

    private Container initAppControllerContainer(ServletConfig config) {
        String webAppRootPackage = config.getInitParameter(MappingResolver.WEB_APP_ROOT_PACKAGE);
        if (Strings.isNullOrEmpty(webAppRootPackage)) {
            throw new IllegalStateException("need webapp_root_package parameter");
        }

        return new ExpressContainer(webAppRootPackage);
    }
}
