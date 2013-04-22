package com.expressmvc.controller;

import java.util.HashMap;
import java.util.Map;

public class Envelope {
    private Map<String, Object> contentsMap = new HashMap<String, Object>();

    public Envelope(Object... initialContents) {
        add(initialContents);
    }

    public <T> T getObjectOf(Class<T> clazz) {
        return (T)contentsMap.get(clazz.getName());
    }

    private void add(Object[] contents) {
        if (contents != null) {
            for (Object contentObject : contents) {
                contentsMap.put(contentObject.getClass().getName(), contentObject);
            }
        }
    }
}
