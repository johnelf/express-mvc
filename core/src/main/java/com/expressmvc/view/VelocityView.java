package com.expressmvc.view;

import com.expressmvc.exception.MergeTemplateException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class VelocityView implements View {
    private String url;

    public VelocityView() {
    }

    @Override
    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Pragma", "private");
        response.setHeader("Cache-Control", "private, must-revalidate");

        Context context = new VelocityContext(model);

        try {
            getTemplate().merge(context, response.getWriter());
        } catch (Exception e) {
            throw new MergeTemplateException();
        }

    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Template getTemplate() throws Exception {
        return Velocity.getTemplate(url, "UTF-8");
    }

}
