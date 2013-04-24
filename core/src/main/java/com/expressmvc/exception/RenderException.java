package com.expressmvc.exception;

public class RenderException extends RuntimeException {
    public RenderException(Exception e) {
        super(e);
    }
}
