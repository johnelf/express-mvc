package com.expressmvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface View {
    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response);
}
