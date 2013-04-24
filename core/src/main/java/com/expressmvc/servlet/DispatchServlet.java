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
    private Container containerOfThisWebApp;
    private MappingResolver mappingResolver;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mappingResolver.init(config);

        Container frameworkContainer = new ExpressContainer("com.expressmvc");
        containerOfThisWebApp = createNewObjectContainer(config, frameworkContainer);
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

        controller.service(req, resp);
    }

<<<<<<< HEAD
    public void setMappingResolver(MappingResolver mappingResolver) {
        this.mappingResolver = mappingResolver;
=======
//        VelocityViewRender velocityView = new VelocityViewRender();
//        velocityView.setViewName("hello.vm");
//        Map<String, Object> model = newHashMap();
//        User siteUser = new BIConversion.User("john");
//        Map<String, Object> model = new HashMap<String, Object>();
//        Article siteUser = new BIConversion.Article("john");
//        model.put("user", siteUser);
//        velocityView.render(model, req, resp);
>>>>>>> add velocity render method
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
            throw new IllegalStateException("need \"webapp_root_package\" parameter in ServletConfig to init webApp.");
        }

        return new ExpressContainer(webAppRootPackage, parentContainer);
    }
}
