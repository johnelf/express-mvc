package com.expressmvc.controller;

import javax.servlet.ServletConfig;
import java.util.Map;

public interface MappingResolver {
    public static final String WEB_APP_ROOT_PACKAGE = "webapp_root_package";

    void init(ServletConfig config);
    Class<? extends AppController> getControllerFor(String url);
}
