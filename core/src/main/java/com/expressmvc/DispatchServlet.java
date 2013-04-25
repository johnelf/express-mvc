package com.expressmvc;

import com.expressioc.Container;
import com.expressioc.ExpressContainer;
import com.expressioc.scope.ContainerAware;
import com.expressmvc.controller.BaseController;
import com.expressmvc.controller.MappingResolver;
import com.google.common.base.Strings;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DispatchServlet extends HttpServlet implements ContainerAware {
    private Container containerOfThisWebApp;
    private MappingResolver mappingResolver;
    private Container containerWhereThisObjectIn;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        Container frameworkContainer = new ExpressContainer("com.expressmvc");
        containerOfThisWebApp = createNewObjectContainer(config, frameworkContainer);

        List<AppInitializer> objectsNeedInit = containerWhereThisObjectIn.getImplementationObjectListOf(AppInitializer.class);
        for (AppInitializer o: objectsNeedInit) {
            o.init(config);
        }
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

        controller.service(req, resp);
    }

    private BaseController getControllerFor(String requestURI) {
        BaseController controller = null;

        Class<? extends BaseController> controllerClazz = mappingResolver.getControllerFor(requestURI);
        if (controllerClazz != null) {
            controller = containerOfThisWebApp.getComponent(controllerClazz);
        }

        return controller;
    }

    private Container createNewObjectContainer(ServletConfig config, Container parentContainer) {
        String webAppRootPackage = config.getInitParameter(MappingResolver.WEB_APP_ROOT_PACKAGE);
        if (Strings.isNullOrEmpty(webAppRootPackage)) {
            throw new IllegalStateException("need webapp_root_package parameter in ServletConfig to init webApp.");
        }

        return new ExpressContainer(webAppRootPackage, parentContainer);
    }

    @Override
    public void awareContainer(Container container) {
        this.containerWhereThisObjectIn = container;
    }

    public void setMappingResolver(MappingResolver mappingResolver) {
        this.mappingResolver = mappingResolver;
    }
}