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
        Class objectClass;
        if (object.getClass().isArray()) {
            objectClass = object.getClass().getComponentType();
            needPluralize = true;
        } else if (object instanceof Collection) {
            needPluralize = true;
            Collection collection = (Collection)object;
            if (collection.isEmpty()) {
                return this;
            } else {
                Object valueToCheck = peekHead(collection);
                objectClass = valueToCheck.getClass();
            }
        } else {
            objectClass = object.getClass();
        }

        contentsMap.put(pluralize(getModelName(objectClass), needPluralize), object);
        return this;
    }

    public String getModelName(Class<?> clazz) {
        if (clazz.isArray()) {
            return clazz.getComponentType().getSimpleName();
        } else {
            return clazz.getSimpleName();
        }
    }

    public String pluralize(String className, boolean pluralize) {
        String attributeName = className.replaceAll("[A-Z]", "_$0").substring(0).toLowerCase();
        return pluralize ? attributeName + "s" : attributeName;
    }

    public final Map<String, Object> getContents() {
        return contentsMap;
    }

    private Object peekHead(Collection collection) {
        if (collection.isEmpty()) {
            throw new IllegalStateException("can not parse name from an empty Collection.");
        }

        Object object = collection.iterator().next();
        if (object == null) {
            throw new IllegalStateException("can not parse name from an empty Collection. Only null object found.");
        }

        return object;
    }

}
