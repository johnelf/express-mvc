package com.expressmvc.view;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;

import java.util.Properties;

public class VelocityConfig {
    public static final String PRE_FIX = "/WEB-INF/views";

    public VelocityEngine velocityEngine;

    public VelocityConfig(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public static void init(String contextPath) {
        Properties properties = new Properties();
        String basePath = System.getProperty("user.dir") + contextPath + PRE_FIX;
        properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, basePath);
        try {
            Velocity.init(properties);
        } catch (Exception e) {
            throw new VelocityException(e);
        }
    }
}
