package com.expressmvc;

import com.expressmvc.model.Model;
import com.expressmvc.view.View;

public class ModelAndView {
    private Model model;
    private View view;

    public boolean hasView() {
        return false;
    }

    public void setViewName(String viewName) {
    }
}
