package com.expressmvc.view;

import com.expressmvc.AppInitializer;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.VelocityException;

import javax.servlet.ServletConfig;
import java.util.Properties;

public class VelocityConfig implements AppInitializer {
    public static final String PRE_FIX = "/WEB-INF/views";
    public static final String TEMPLATE_POSTFIX = ".vm";

    @Override
    public void init(ServletConfig config) {
        Properties properties = new Properties();
        String basePath = System.getProperty("user.dir") + config.getServletContext().getContextPath() + PRE_FIX;
        properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, basePath);
        try {
            Velocity.init(properties);
        } catch (Exception e) {
            throw new VelocityException(e);
        }
    }

}
