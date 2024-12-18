package com.Dennis.BookApp.exceptionhandler;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Set;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {

    private String error;
    private String businessErrorCode;
    private String businessErrorDescription;

private Set<String>validationErrors;





}
