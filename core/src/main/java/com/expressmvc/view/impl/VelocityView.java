package com.expressmvc.view.impl;

import com.expressmvc.exception.RenderException;
import com.expressmvc.view.View;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class VelocityView implements View {
    private final String viewFile;

    public VelocityView(String viewFile) {
        this.viewFile = viewFile;
    }

    @Override
    public void render(Map<String, Object> modelMap, HttpServletRequest request, HttpServletResponse response) {
        Context context = new VelocityContext(modelMap);
        try {
            Velocity.getTemplate(viewFile).merge(context, response.getWriter());
        } catch (Exception e) {
            throw new RenderException(e);
        }
    }
}
