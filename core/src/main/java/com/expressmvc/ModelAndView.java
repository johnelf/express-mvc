package com.expressmvc;

import java.util.Collections;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class ModelAndView {
    private Map<String, Object> viewIngredients = newHashMap();

    public boolean hasView() {
        return false;
    }

    public void setViewName(String viewName) {
    }

    public void addViewIngredient(String name, Object ingredient) {
        viewIngredients.put(name, ingredient);
    }

    public final Map<String, Object> getViewIngredients() {
        return Collections.unmodifiableMap(viewIngredients);
    }
}
