package com.expressmvc.model;

import com.expressioc.utility.ClassUtility;

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
        boolean pluralize = false;
        Class objectClass;
        if (object.getClass().isArray()) {
            objectClass = object.getClass().getComponentType();
            pluralize = true;
        } else if (object instanceof Collection) {
            pluralize = true;
            Collection collection = (Collection)object;
            if (collection.isEmpty()) {
                return this;
            } else {
                Object valueToCheck = peekHead(collection);
                objectClass = ClassUtility.getDomainClass(valueToCheck);
            }
        } else {
            objectClass = object.getClass();
        }

        String name = getShortNameByClass(objectClass);
        return addAttribute(getAttributeName(name, pluralize), object);
    }

    public String getShortNameByClass(Class<?> clazz) {
        String shortName = getShortName(clazz);
        checkNotNull(shortName);
        int lastDotIndex = shortName.lastIndexOf('.');
        return shortName.substring(lastDotIndex + 1).replace('$', '.');
    }

    public String getAttributeName(String className, boolean pluralize) {
        checkNotNull(className);

        String attributeName = className.replaceAll("[A-Z]", "$0").substring(0).toLowerCase();
        return pluralize ? attributeName + "s" : attributeName;
    }

    public final Map<String, Object> getContents() {
        return contentsMap;
    }

    private ModelContainer addAttribute(String attributeName, Object object) {
        contentsMap.put(attributeName, object);
        return this;
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

    private String getShortName(Class<?> clazz) {
        checkNotNull(clazz);

        if (clazz.isArray()) {
            return getShortNameForArray(clazz);
        } else {
            return clazz.getName();
        }
    }

    private String getShortNameForArray(Class<?> clazz) {
        clazz = clazz.getComponentType();
        return clazz.getName();
    }
}
