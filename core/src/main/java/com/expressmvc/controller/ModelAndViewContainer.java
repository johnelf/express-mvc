package com.expressmvc.controller;

import com.expressmvc.model.ModelNameResolver;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static org.junit.Assert.assertNotNull;

public class ModelAndViewContainer {
    private Map<String, Object> contentsMap = newHashMap();

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
            pluralize = true;
            Collection collection = (Collection)object;
            if (collection.isEmpty()) {
                return this;
            } else {
                Object valueToCheck = ModelNameResolver.peekHead(collection);
                objectClass = ModelNameResolver.getClassForValue(valueToCheck);
            }
        } else {
            objectClass = object.getClass();
        }

        String name = ModelNameResolver.getShortNameByClass(objectClass);
        return addAttribute(ModelNameResolver.getAttributeName(name, pluralize), object);
    }

    private ModelAndViewContainer addAttribute(String attributeName, Object object) {
        contentsMap.put(attributeName, object);
        return this;
    }

    public final Map<String, Object> getContents() {
        return contentsMap;
    }
}
