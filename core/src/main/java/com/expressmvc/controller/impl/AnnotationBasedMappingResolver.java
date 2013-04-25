package com.expressmvc.controller.impl;

import com.expressioc.annotation.Singleton;
import com.expressmvc.AppInitializer;
import com.expressmvc.annotation.Path;
import com.expressmvc.controller.AppController;
import com.expressmvc.controller.MappingResolver;
import com.google.common.reflect.ClassPath;

import javax.servlet.ServletConfig;
import java.io.IOException;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@Singleton
public class AnnotationBasedMappingResolver implements MappingResolver, AppInitializer {
    private Map<String, Class<? extends AppController>> urlHandlerMapping = newHashMap();
    private String contextPath;

    @Override
    public void init(ServletConfig config) {
        contextPath = config.getServletContext().getContextPath();
        resolveControllerMapping(config.getInitParameter(WEB_APP_ROOT_PACKAGE));
    }

    @Override
    public Class<? extends AppController> getControllerFor(String url) {
        return urlHandlerMapping.get(url);
    }

    private void resolveControllerMapping(String packageNameToScan) {
        try {
            ClassPath classpath = ClassPath.from(ClassLoader.getSystemClassLoader());
            for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive(packageNameToScan)) {
                Class<?> clazz = classInfo.load();

                boolean needMapping = AppController.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(Path.class);
                if (needMapping) {
                    String urlToHandle = clazz.getAnnotation(Path.class).value();
                    addUrlHandlerMapping((Class<? extends AppController>) clazz, urlToHandle);
                }

            }
        } catch (IOException e) {
        }
    }

    private void addUrlHandlerMapping(Class<? extends AppController> clazz, String urlToHandle) {
        urlHandlerMapping.put(contextPath + urlToHandle, clazz);
    }
}
