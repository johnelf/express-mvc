package com.expressmvc.view;


import javax.servlet.http.HttpServletRequest;

public interface ViewResolver {
    View findView(HttpServletRequest request, String viewName);
}
