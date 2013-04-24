package com.expressmvc;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class ModelAndView {
    private Map<String, Object> viewIngredients = newHashMap();
    private String viewTemplateName;

    public ModelAndView(HttpServletRequest req) {
        this.viewTemplateName = getConventionalViewTemplate(req);
    }

    private String getConventionalViewTemplate(HttpServletRequest request) {
        //String pathInContext = request.getRequestURI().substring(request.getContextPath().length());
        //return request.getContextPath() + "/WEB-INF/views" + pathInContext + "/article.vm"; //TODO
        return "/article/article.vm";
    }

    public void addViewIngredient(String name, Object ingredient) {
        viewIngredients.put(name, ingredient);
    }

    public final Map<String, Object> getViewIngredients() {
        return Collections.unmodifiableMap(viewIngredients);
    }

    public String getViewTemplateName() {
        return viewTemplateName;
    }
}
