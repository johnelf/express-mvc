package com.expressmvc.controller;

import java.util.Iterator;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class Envelope {
    private Map<String, Object> contentsMap = newHashMap();

    public Envelope(Object... initialContents) {
        add(initialContents);
    }

    public <T> T getObjectOf(Class<T> clazz) {
        return (T)contentsMap.get(clazz.getName());
    }

    public Envelope add(Object... contents) {
        if (contents != null) {
            for (Object contentObject : contents) {
                contentsMap.put(contentObject.getClass().getName(), contentObject);
            }
        }

        return this;
    }

    public Iterator getContentsIterator() {
        return contentsMap.values().iterator();
    }
}
