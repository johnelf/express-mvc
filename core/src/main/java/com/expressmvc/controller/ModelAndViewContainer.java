package com.expressmvc.controller;

import java.io.Externalizable;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static org.junit.Assert.assertNotNull;

public class ModelAndViewContainer {
    private Map<String, Object> contentsMap = newHashMap();
    private static final Set<Class> ignoredInterfaces = new HashSet<Class>();

    static {
        ignoredInterfaces.add(Serializable.class);
        ignoredInterfaces.add(Externalizable.class);
        ignoredInterfaces.add(Cloneable.class);
        ignoredInterfaces.add(Comparable.class);
    }

    public ModelAndViewContainer(Object initialContents) {
        add(initialContents);
    }

    public ModelAndViewContainer() {
    }

    public static ModelAndViewContainer initWith(Object object) {
        return new ModelAndViewContainer(object);
    }

    public static ModelAndViewContainer initWith() {
        return new ModelAndViewContainer();
    }

    public ModelAndViewContainer add(Object object) {
        checkNotNull(object);
        boolean pluralize = false;
        Class objectClass;
        if (object.getClass().isArray()) {
            objectClass = object.getClass().getComponentType();
            pluralize = true;
        } else if (object instanceof Collection) {
            Collection collection = (Collection)object;
            if (collection.isEmpty()) {
                throw new IllegalArgumentException("can not get a variable name from a empty Collection.");
            }
            Object valueToCheck = peekHead(collection);
            objectClass = getClassForValue(valueToCheck);
            pluralize = true;
        } else {
            objectClass = object.getClass();
        }

        String name = getShortNameByClass(objectClass);
        return addAttribute(getAttributeName(name, pluralize), object);
    }

    private String getShortNameByClass(Class<?> clazz) {
        String shortName = getShortName(clazz);
        checkNotNull(shortName);
        int lastDotIndex = shortName.lastIndexOf('.');
        return shortName.substring(lastDotIndex + 1).replace('$', '.');
    }

    private String getShortName(Class<?> clazz) {
        checkNotNull(clazz, "Class must not be null.");
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

    private Class getClassForValue(Object value) {
        Class clazz = value.getClass();
        if (Proxy.isProxyClass(clazz)) {
            Class[] ifcs = clazz.getInterfaces();
            for (Class ifc:ifcs){
                if (!ignoredInterfaces.contains(ifc)) {
                    return ifc;
                }
            }
        } else if (clazz.getName().lastIndexOf('$') != -1 && clazz.getDeclaringClass() == null) {
            clazz = clazz.getSuperclass();
        }
        return clazz;
    }

    private Object peekHead(Collection collection) {
        Iterator iterator = collection.iterator();
        if (!iterator.hasNext()) {
            throw new IllegalStateException("can not parse name from an empty Collection.");
        }
        Object object = iterator.next();
        if (object == null) {
            throw new IllegalStateException("can not parse name from an empty Collection. Only null object found.");
        }
        return object;
    }

    public String getAttributeName(String className, boolean pluralize) {
        checkNotNull(className);

        String attributeName = className.replaceAll("[A-Z]", "$0").substring(0).toLowerCase();
        return pluralize ? attributeName + "s" : attributeName;
    }

    private ModelAndViewContainer addAttribute(String attributeName, Object object) {
        contentsMap.put(attributeName, object);
        return this;
    }

    public final Map<String, Object> getContents() {
        return contentsMap;
    }
}
