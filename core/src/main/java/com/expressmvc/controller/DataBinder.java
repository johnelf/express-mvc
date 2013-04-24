package com.expressmvc.controller;

import com.expressmvc.exception.DataBindException;

import javax.servlet.http.HttpServletRequest;

public interface DataBinder<T> {
    void bind(HttpServletRequest request, T parameter) throws DataBindException;
}
