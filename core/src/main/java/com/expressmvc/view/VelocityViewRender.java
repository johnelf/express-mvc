package com.expressmvc.view;

import com.expressmvc.ModelAndView;
import com.expressmvc.exception.RenderException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VelocityViewRender implements ViewRender {
    @Override
    public void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
        Context context = new VelocityContext(mv.getViewIngredients());
        try {
            getTemplate(mv.getTemplate()).merge(context, response.getWriter());
        } catch (Exception e) {
            throw new RenderException(e);
        }
    }

    public Template getTemplate(String url) throws Exception {
        return Velocity.getTemplate(url, "UTF-8");
    }

}
