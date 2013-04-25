package com.expressmvc.view;


public interface ViewResolver {
    View findView(String viewName);
}
