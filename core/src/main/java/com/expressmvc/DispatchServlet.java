package com.expressmvc;

import com.expressioc.Container;
import com.expressioc.ExpressContainer;
import com.expressioc.scope.ContainerAware;
import com.expressmvc.controller.AppController;
import com.expressmvc.controller.MappingResolver;
import com.expressmvc.view.View;
import com.expressmvc.view.ViewResolver;
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
    private List<ViewResolver> viewResolvers;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        Container frameworkContainer = new ExpressContainer("com.expressmvc");
        containerOfThisWebApp = createNewObjectContainer(config, frameworkContainer);

        initFrameworkComponents(config);

        viewResolvers = containerWhereThisObjectIn.getImplementationObjectListOf(ViewResolver.class);
    }

    private void initFrameworkComponents(ServletConfig config) {
        List<AppInitializer> initializerList = containerWhereThisObjectIn.getImplementationObjectListOf(AppInitializer.class);
        for (AppInitializer initializer: initializerList) {
            initializer.init(config);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        AppController controller = getControllerFor(req.getRequestURI());
        if (controller == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        ModelAndView mv = controller.doService(req, resp);
        View view = resolveView(req, mv);
        view.render(mv.getModels(), req, resp);
    }

    private View resolveView(HttpServletRequest req, ModelAndView mv) throws ServletException {
        for (ViewResolver viewResolver : viewResolvers) {
            View view = viewResolver.findView(req, mv.getViewName());
            if (view != null) {
                return view;
            }
        }

        throw new ServletException("can not resolve view for:" + req.getRequestURL());
    }

    private AppController getControllerFor(String requestURI) {
        Class<? extends AppController> controllerClazz = mappingResolver.getControllerFor(requestURI);
        if (controllerClazz != null) {
            return containerOfThisWebApp.getComponent(controllerClazz);
        }

        return null;
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
