package com.expressmvc.view.impl;

import com.expressioc.annotation.Singleton;
import com.expressmvc.view.View;
import com.expressmvc.view.ViewResolver;

@Singleton
public class DefaultViewResolver implements ViewResolver{
    //TODO web-inf as prefix

    @Override
    public View findView(String viewName) {
        return null;
    }

}
