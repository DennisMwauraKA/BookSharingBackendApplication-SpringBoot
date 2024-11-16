package com.Dennis.BookApp.exceptionhandler;


import com.fasterxml.jackson.annotation.JsonInclude;


import java.util.Map;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {

    private String error;
    private String businessErrorCode;
    private String businessErrorDescription;
    private Set<String> validationErrors;
    private Map<String, String> errors;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getBusinessErrorCode() {
        return businessErrorCode;
    }

    public void setBusinessErrorCode(String businessErrorCode) {
        this.businessErrorCode = businessErrorCode;
    }

    public String getBusinessErrorDescription() {
        return businessErrorDescription;
    }

    public void setBusinessErrorDescription(String businessErrorDescription) {
        this.businessErrorDescription = businessErrorDescription;
    }

    public Set<String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Set<String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }


    public ExceptionResponse(String error, String businessErrorCode, Set<String> validationErrors, String businessErrorDescription, Map<String, String> errors) {
        this.error = error;
        this.businessErrorCode = businessErrorCode;
        this.validationErrors = validationErrors;
        this.businessErrorDescription = businessErrorDescription;
        this.errors = errors;
    }
}
