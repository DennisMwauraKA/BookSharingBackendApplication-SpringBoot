package com.Dennis.BookApp.exceptionhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
public ResponseEntity<ExceptionResponse>handleException(){

}
}
