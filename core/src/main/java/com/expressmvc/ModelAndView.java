package com.expressmvc;

import java.util.Collection;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;

public class ModelAndView {
    private String viewName;
    private Map<String, Object> models = newHashMap();

    public ModelAndView() {
    }

    public ModelAndView(Object initialContents) {
        add(initialContents);
    }

    public static ModelAndView initWith(Object object) {
        return new ModelAndView(object);
    }

    public ModelAndView setViewName(String viewName) {
        this.viewName = viewName;
        return this;
    }

    public String getViewName() {
        return viewName;
    }

    public final Map<String, Object> getModels() {
        return models;
    }

    public ModelAndView add(Object object) {
        checkNotNull(object);
        boolean needPluralize = false;
        Class objectClass = object.getClass();

        if (object.getClass().isArray()) {
            objectClass = object.getClass().getComponentType();
            needPluralize = true;
        } else if (object instanceof Collection) {
            needPluralize = true;
            Collection collection = (Collection)object;
            if (!collection.isEmpty()) {
                objectClass = collection.iterator().next().getClass();
            }
        }

        models.put(getModelName(objectClass, needPluralize), object);
        return this;
    }

    private String getModelName(Class objectClass, boolean needPluralize) {
        String modelName = underscore(getModelName(objectClass));
        return needPluralize ? modelName + "s" : modelName;
    }

    private String underscore(String className) {
        return className.replaceAll("[A-Z]", "_$0").substring(1).toLowerCase();
    }

    public String getModelName(Class<?> clazz) {
        if (clazz.isArray()) {
            return clazz.getComponentType().getSimpleName();
        } else {
            return clazz.getSimpleName();
        }
    }
}
