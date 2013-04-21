package com.expressmvc.controller;

import com.expressmvc.annotation.Path;
import com.google.common.reflect.ClassPath;

import javax.servlet.ServletConfig;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AnnotationBasedMappingResolver implements MappingResolver {
    public static final String PACKAGE_TO_SCAN = "package-to-scan";
    private Map<String, Class> urlHandlerMapping = new HashMap<String, Class>();
    private String contextPath;

    @Override
    public void init(ServletConfig config) {
        String scanPath = config.getInitParameter(PACKAGE_TO_SCAN);
        contextPath = config.getServletContext().getContextPath();
        resolveControllerMapping(scanPath);
    }

    @Override
    public Class getControllerFor(String url) {
        return urlHandlerMapping.get(url);
    }

    private void resolveControllerMapping(String packageNameToScan) {
        try {
            ClassPath classpath = ClassPath.from(ClassLoader.getSystemClassLoader());
            for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive(packageNameToScan)) {
                Class<?> clazz = classInfo.load();
                if (clazz.isAnnotationPresent(Path.class)) {
                    String urlToHandle = clazz.getAnnotation(Path.class).value();
                    addUrlHandlerMapping(clazz, urlToHandle);
                }
            }
        } catch (IOException e) {
        }
    }

    private void addUrlHandlerMapping(Class<?> clazz, String urlToHandle) {
        urlHandlerMapping.put(contextPath + urlToHandle, clazz);
    }
}
