package com.Dennis.BookApp.exceptionhandler;

import org.springframework.http.HttpStatus;

public enum BusinessErrorCodes {;



    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    BusinessErrorCodes(int code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
