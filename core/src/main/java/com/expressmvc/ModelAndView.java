package com.expressmvc;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class ModelAndView {
    private String viewName;
    private Map<String, Object> models = newHashMap();

    public ModelAndView() {
    }

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public void addModel(String name, Object model) {
        models.put(name, model);
    }

    public final Map<String, Object> getModels() {
        return models;
    }

    public String getViewName() {
        return viewName;
    }

}
