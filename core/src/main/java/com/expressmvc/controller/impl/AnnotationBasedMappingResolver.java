package com.expressmvc.controller.impl;

import com.expressioc.annotation.Singleton;
import com.expressmvc.AppInitializer;
import com.expressmvc.annotation.Path;
import com.expressmvc.controller.AppController;
import com.expressmvc.controller.MappingResolver;
import com.google.common.reflect.ClassPath;
import com.sun.xml.internal.ws.util.StringUtils;

import javax.servlet.ServletConfig;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@Singleton
public class AnnotationBasedMappingResolver implements MappingResolver, AppInitializer {
    private Map<String, Map<String, Class<? extends AppController>>> urlHandlerMapping = newHashMap();
    private String contextPath;

    @Override
    public void init(ServletConfig config) {
        contextPath = config.getServletContext().getContextPath();
        resolveControllerMapping(config.getInitParameter(WEB_APP_ROOT_PACKAGE));
    }

    @Override
    public Class<? extends AppController> getControllerFor(String url) {
        for (String baseUrl : urlHandlerMapping.keySet()) {
            if (url.startsWith(baseUrl)) {
                return findAppController(baseUrl);
            }
        }
        return null;
    }

    private Class<? extends AppController> findAppController(String baseUrl) {
        Map<String, Class<? extends AppController>> pathMapInController = urlHandlerMapping.get(baseUrl);
        for (Class<? extends AppController> controller : pathMapInController.values()) {
            if (controller != null) {
                return controller;
            }
        }
        return null;
    }

    private void resolveControllerMapping(String packageNameToScan) {
        try {
            ClassPath classpath = ClassPath.from(ClassLoader.getSystemClassLoader());
            for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive(packageNameToScan)) {
                Class<?> clazz = classInfo.load();

                boolean needMapping = AppController.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(Path.class);
                if (needMapping) {
                    addUrlHandlerMappingBasedOnMethods(clazz);
                }

            }
        } catch (IOException e) {
        }
    }

    private void addUrlHandlerMappingBasedOnMethods(Class<?> clazz) {
        String baseUrl = clazz.getAnnotation(Path.class).value();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Path.class)) {
                addUrlHandlerMapping((Class<? extends AppController>)clazz, baseUrl, method.getAnnotation(Path.class).value());
            }
        }
    }

    private void addUrlHandlerMapping(Class<? extends AppController> clazz, String baseUrl, String methodUrl) {
        Map<String, Class<? extends AppController>> methodMapping = urlHandlerMapping.get(contextPath + baseUrl);
        if (methodMapping != null) {
            methodMapping.put(methodUrl, clazz);
        } else {
            HashMap<String, Class<? extends AppController>> urlToPath = new HashMap<String, Class<? extends AppController>>();
            urlToPath.put(methodUrl, clazz);
            urlHandlerMapping.put(contextPath + baseUrl, urlToPath);
        }
    }
}
