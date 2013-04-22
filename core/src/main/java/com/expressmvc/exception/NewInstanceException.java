package com.expressmvc.exception;

public class NewInstanceException extends RuntimeException{
    public NewInstanceException(Exception e) {
        super(e);
    }
}
