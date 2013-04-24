package com.expressmvc.controller;

import javax.servlet.ServletConfig;

public interface MappingResolver {
    public static final String WEB_APP_ROOT_PACKAGE = "webapp_root_package";

    void init(ServletConfig config);
    Class<? extends BaseController> getControllerFor(String url);
}
