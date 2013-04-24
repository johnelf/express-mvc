package com.expressmvc.view;

import com.expressmvc.ModelAndView;
import com.expressmvc.exception.MergeTemplateException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VelocityViewRender implements ViewRender {
    private String viewName;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Pragma", "private");
        response.setHeader("Cache-Control", "private, must-revalidate");

        Context context = new VelocityContext(mv.getViewIngredients());

        try {
            getTemplate(mv.getViewTemplateName()).merge(context, response.getWriter());
        } catch (Exception e) {
            throw new MergeTemplateException();
        }

    }

    public Template getTemplate(String url) throws Exception {
        return Velocity.getTemplate(url, "UTF-8");
    }

}
