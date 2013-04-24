package com.expressmvc.controller.impl;

import com.expressmvc.annotation.Path;
import com.expressmvc.controller.BaseController;
import com.expressmvc.controller.MappingResolver;
import com.google.common.base.Strings;
import com.google.common.reflect.ClassPath;

import javax.servlet.ServletConfig;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Maps.newHashMap;

public class AnnotationBasedMappingResolver implements MappingResolver {
    private Map<String, Class<? extends BaseController>> urlHandlerMapping = newHashMap();
    private String contextPath;

    @Override
    public void init(ServletConfig config) {
        String webAppRootPackage = config.getInitParameter(WEB_APP_ROOT_PACKAGE);
        if (Strings.isNullOrEmpty(webAppRootPackage)) {  //TODO DRY
            throw new IllegalStateException("need \"webapp_root_package\" parameter in ServletConfig to init webApp.");
        }

        contextPath = config.getServletContext().getContextPath();
        resolveControllerMapping(webAppRootPackage);
    }

    @Override
    public Class<? extends BaseController> getControllerFor(String url) {
        return urlHandlerMapping.get(url);
    }

    private void resolveControllerMapping(String packageNameToScan) {
        try {
            ClassPath classpath = ClassPath.from(ClassLoader.getSystemClassLoader());
            for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive(packageNameToScan)) {
                Class<?> clazz = classInfo.load();
                boolean isControllerWithAnnotation = BaseController.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(Path.class);

                if (isControllerWithAnnotation) {
                    String urlToHandle = clazz.getAnnotation(Path.class).value();
                    addUrlHandlerMapping((Class<? extends BaseController>) clazz, urlToHandle);
                }
            }
        } catch (IOException e) {
        }
    }

    private void addUrlHandlerMapping(Class<? extends BaseController> clazz, String urlToHandle) {
        urlHandlerMapping.put(contextPath + urlToHandle, clazz);
    }
}
