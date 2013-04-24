package com.expressmvc;

import com.expressmvc.view.VelocityConfig;
import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class ModelAndView {
    public static final String DEFAULT_VIEW = "/show.vm";
    private Map<String, Object> viewIngredients = newHashMap();
    private String viewTemplate;

    public ModelAndView(HttpServletRequest req) {
        this(req, null);
    }

    public ModelAndView(HttpServletRequest req, String viewName) {
        String viewTemplateName = Strings.isNullOrEmpty(viewName) ? DEFAULT_VIEW : viewName + VelocityConfig.TEMPLATE_POSTFIX;
        this.viewTemplate = getPathInContext(req) + "/" + viewTemplateName;
    }

    private String getPathInContext(HttpServletRequest request) {
        return request.getRequestURI().substring(request.getContextPath().length());
    }

    public void addViewIngredient(String name, Object ingredient) {
        viewIngredients.put(name, ingredient);
    }

    public final Map<String, Object> getViewIngredients() {
        return Collections.unmodifiableMap(viewIngredients);
    }

    public String getTemplate() {
        return viewTemplate;
    }
}
