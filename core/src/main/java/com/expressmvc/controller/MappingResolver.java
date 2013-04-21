package com.expressmvc.controller;

import javax.servlet.ServletConfig;

public interface MappingResolver {
    void init(ServletConfig config);
    Class<? extends BaseController> getControllerFor(String url);
}
