package com.Dennis.BookApp.exceptionhandler;


import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {

    private String error;
    private String businessErrorCode;
    private String businessErrorDescription;

    public ExceptionResponse(String error, String businessErrorCode, String businessErrorDescription) {
        this.error = error;
        this.businessErrorCode = businessErrorCode;
        this.businessErrorDescription = businessErrorDescription;
    }

    public ExceptionResponse() {

    }

    public void setError(String error) {
        this.error = error;
    }

    public void setBusinessErrorCode(String businessErrorCode) {
        this.businessErrorCode = businessErrorCode;
    }

    public void setBusinessErrorDescription(String businessErrorDescription) {
        this.businessErrorDescription = businessErrorDescription;
    }


}
