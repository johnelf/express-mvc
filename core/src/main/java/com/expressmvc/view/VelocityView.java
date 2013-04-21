package com.expressmvc.view;

import javax.security.auth.login.Configuration;
import java.io.Writer;
import java.util.Map;

public class VelocityView implements View{
    private final String SUFFIX = ".jsp";
    private Configuration config;
    private String configName;

    public VelocityView(Configuration config, String configName) {
        this.config = config;
        this.configName = configName;
    }

    @Override
    public void render(Writer writer, Map<String, Object> model) {
    }
}
