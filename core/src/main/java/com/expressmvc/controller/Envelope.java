package com.expressmvc.controller;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

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
                contentsMap.put(contentObject.getClass().getName(), contentObject);
            }
        }

        return this;
    }

    public Iterator getContentsIterator() {
        return contentsMap.values().iterator();
    }

    public final Map<String, Object> getContents() {
        return Collections.unmodifiableMap(contentsMap);
    }
}
