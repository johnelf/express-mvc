package com.expressmvc.controller;

import com.expressmvc.annotation.ViewIngredient;

import java.util.*;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

public class Envelope {
    private Map<String, Object> contentsMap = newHashMap();

    public Envelope(Object... initialContents) {
        add(initialContents);
    }

    public static Envelope initWith(Object... objects) {
        return new Envelope(objects);
    }

    public Envelope add(Object... contents) {
        if (contents != null) {
            for (Object contentObject : contents) {
                if (contentObject instanceof List) {
                    if (((List) contentObject).size() > 0) {
                        contentsMap.put(((ArrayList) contentObject).get(0).getClass().getAnnotation(ViewIngredient.class).value(), contentObject);
                    } else {
                        contentsMap.put(((List) contentObject).get(0).getClass().getAnnotation(ViewIngredient.class).value(), newArrayList());
                    }
                } else {
                    ViewIngredient viewIngredient = contentObject.getClass().getAnnotation(ViewIngredient.class);
                    if (viewIngredient != null) {
                        contentsMap.put(viewIngredient.value(), contentObject);
                    } else {
                        contentsMap.put(contentObject.getClass().getName(), contentObject);
                    }
                }
            }
        }

        return this;
    }

    public Iterator getContentsIterator() {
        return contentsMap.values().iterator();
    }

    public final Map<String, Object> getContents() {
        return contentsMap;
    }
}
