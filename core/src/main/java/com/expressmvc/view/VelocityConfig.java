package com.expressmvc.view;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.util.Properties;

public class VelocityConfig {
    public static final String PRE_FIX = "/WEB-INF/templates";

    public VelocityEngine ve;

    public VelocityConfig(VelocityEngine ve) {
        this.ve = ve;
    }

    public static void init(String contextPath) {
        Properties properties = new Properties();
        String basePath = System.getProperty("user.dir") + contextPath + PRE_FIX;
        properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, basePath);
        properties.put("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
        properties.put("runtime.log.logsystem.log4j.category", "velocity");
        properties.put("runtime.log.logsystem.log4j.logger", "velocity");
        try {
            Velocity.init(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
