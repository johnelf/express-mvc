package com.expressmvc.servlet;

import com.expressmvc.controller.AnnotationBasedMappingResolver;
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
    private MappingResolver mappingResolver = new AnnotationBasedMappingResolver();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mappingResolver.init(config);
        VelocityConfig.init(getServletContext().getContextPath());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        VelocityView velocityView = new VelocityView();
        velocityView.setUrl("hello.vm");
        Map<String, Object> model = new HashMap<String, Object>();
        User siteUser = new User("john");
        model.put("user", siteUser);
        velocityView.render(model, req, resp);
    }

}
