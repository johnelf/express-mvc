package com.expressmvc.binder;

import javax.servlet.http.HttpServletRequest;

public interface DataBinder<T> {
    void bind(HttpServletRequest request, T parameter);
}
