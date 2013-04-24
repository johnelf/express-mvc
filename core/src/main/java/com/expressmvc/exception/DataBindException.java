package com.expressmvc.exception;

public class DataBindException extends RuntimeException {
    public DataBindException(Exception e) {
        super(e);
    }
}
