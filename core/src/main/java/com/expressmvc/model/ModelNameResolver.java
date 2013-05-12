package com.expressmvc.model;

import java.io.Externalizable;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class ModelNameResolver {
    public static final Set<Class> ignoredInterfaces = new HashSet<Class>();

    static {
        ModelNameResolver.ignoredInterfaces.add(Serializable.class);
        ModelNameResolver.ignoredInterfaces.add(Externalizable.class);
        ModelNameResolver.ignoredInterfaces.add(Cloneable.class);
        ModelNameResolver.ignoredInterfaces.add(Comparable.class);
    }

    public static Object peekHead(Collection collection) {
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

    public static Class getClassForValue(Object value) {
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

    public static String getShortNameForArray(Class<?> clazz) {
        clazz = clazz.getComponentType();
        return clazz.getName();
    }

    public static String getShortName(Class<?> clazz) {
        checkNotNull(clazz, "Class must not be null.");
        if (clazz.isArray()) {
            return getShortNameForArray(clazz);
        } else {
            return clazz.getName();
        }
    }

    public static String getShortNameByClass(Class<?> clazz) {
        String shortName = getShortName(clazz);
        checkNotNull(shortName);
        int lastDotIndex = shortName.lastIndexOf('.');
        return shortName.substring(lastDotIndex + 1).replace('$', '.');
    }

    public static String getAttributeName(String className, boolean pluralize) {
        checkNotNull(className);

        String attributeName = className.replaceAll("[A-Z]", "$0").substring(0).toLowerCase();
        return pluralize ? attributeName + "s" : attributeName;
    }
}
