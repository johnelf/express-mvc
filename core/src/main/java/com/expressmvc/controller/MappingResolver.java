package com.expressmvc.controller;

import javax.servlet.ServletConfig;

public interface MappingResolver {
    void init(ServletConfig config);
    Class getControllerFor(String url);
}
