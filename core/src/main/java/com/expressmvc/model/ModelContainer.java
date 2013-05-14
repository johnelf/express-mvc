package com.expressmvc.model;

import java.util.Collection;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;

public class ModelContainer {
    private Map<String, Object> contentsMap = newHashMap();

    public ModelContainer() {
    }

    public ModelContainer(Object initialContents) {
        add(initialContents);
    }

    public static ModelContainer initWith(Object object) {
        return new ModelContainer(object);
    }

    public static ModelContainer initWith() {
        return new ModelContainer();
    }

    public ModelContainer add(Object object) {
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

        contentsMap.put(getModelName(objectClass, needPluralize), object);
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

    public final Map<String, Object> getContents() {
        return contentsMap;
    }

}
