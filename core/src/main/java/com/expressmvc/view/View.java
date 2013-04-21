package com.expressmvc.view;

import java.io.Writer;
import java.util.Map;

public interface View {
    public void render(Writer writer, Map<String, Object> model);
}
