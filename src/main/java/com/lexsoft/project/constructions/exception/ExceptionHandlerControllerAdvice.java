package com.lexsoft.project.constructions.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.lexsoft.project.constructions.exception.model.ExceptionResponse;
import com.lexsoft.project.constructions.exception.types.InternalWebException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InternalWebException.class})
    protected ResponseEntity<Object> handleConflict(InternalWebException ex, WebRequest request){

        ExceptionResponse response = new ExceptionResponse(new Date().getTime(),ex.getErrors());
        return handleExceptionInternal(ex, response, new HttpHeaders(), ex.getHttpStatus(), request);

    }

}
