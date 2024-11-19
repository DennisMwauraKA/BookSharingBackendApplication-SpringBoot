package com.Dennis.BookApp.exceptionhandler;

public class OperationNotPermittedException extends RuntimeException {
    public OperationNotPermittedException(String msg) {

        super(msg);
    }
}
