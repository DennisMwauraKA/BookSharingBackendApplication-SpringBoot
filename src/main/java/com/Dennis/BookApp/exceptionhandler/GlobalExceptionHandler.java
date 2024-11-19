package com.Dennis.BookApp.exceptionhandler;

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
        BusinessErrorCodes errorCode = BusinessErrorCodes.ACCOUNT_LOCKED;

        // generate the exception responses using Exception response object
        ExceptionResponse response = new ExceptionResponse();
        response.setError("Authentication Error");
        response.setBusinessErrorCode(String.valueOf(errorCode.getCode()));
        response.setBusinessErrorDescription(errorCode.getDescription());

        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);

    }




    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
        BusinessErrorCodes errorCode = BusinessErrorCodes.ACCOUNT_DISABLED;

        // generate the exception responses using Exception response object
        ExceptionResponse response = new ExceptionResponse();
        response.setError("Account Disabled");
        response.setBusinessErrorCode(String.valueOf(errorCode.getCode()));
        response.setBusinessErrorDescription(errorCode.getDescription());

        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);

    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp) {
        BusinessErrorCodes errorCode = BusinessErrorCodes.BAD_CREDENTIALS;

        // generate the exception responses using Exception response object
        ExceptionResponse response = new ExceptionResponse();
        response.setError("Invalid credentials");
        response.setBusinessErrorCode(String.valueOf(errorCode.getCode()));
        response.setBusinessErrorDescription(errorCode.getDescription());

        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);

    }





    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
        BusinessErrorCodes errorCode = BusinessErrorCodes.SERVER_ERROR;

        // generate the exception responses using Exception response object
        ExceptionResponse response = new ExceptionResponse();
        response.setError("SERVER ERROR");
        response.setBusinessErrorCode(String.valueOf(errorCode.getCode()));
        response.setBusinessErrorDescription(errorCode.getDescription());

        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);

    }


    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException exp) {
        BusinessErrorCodes errorCode = BusinessErrorCodes.SERVER_ERROR;

        // generate the exception responses using Exception response object
        ExceptionResponse response = new ExceptionResponse();
        response.setError("SERVER ERROR");
        response.setBusinessErrorCode(String.valueOf(errorCode.getCode()));
        response.setBusinessErrorDescription(errorCode.getDescription());

        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);

    }


}
