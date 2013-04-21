package com.expressmvc.controller;

import javax.servlet.ServletConfig;

public class AnnotationBasedMappingResolver implements MappingResolver {
    public static final String CONTROLLER_SCAN_PATH = "controller-scan-path";

    public void init(ServletConfig config) {
        String scanPath = config.getInitParameter(CONTROLLER_SCAN_PATH);
        resolveControllerMapping(scanPath);
    }

    private void resolveControllerMapping(String scanPath) {
    }


    public BaseController getControllerFor(String url) {
        return null;
    }
}
