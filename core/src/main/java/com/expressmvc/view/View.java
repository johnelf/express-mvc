package com.expressmvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface View {
    void render(Map<String, Object> modelMap, HttpServletRequest req, HttpServletResponse resp);
}
