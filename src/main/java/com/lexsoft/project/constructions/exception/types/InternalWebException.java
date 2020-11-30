package com.lexsoft.project.constructions.exception.types;

import com.lexsoft.project.constructions.exception.model.ErrorMessage;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
public class InternalWebException extends RuntimeException {

    List<ErrorMessage> errors;
    HttpStatus httpStatus;

    public InternalWebException(HttpStatus httpStatus, List<ErrorMessage> errors) {
        super();
        this.errors = errors;
        this.httpStatus = httpStatus;
    }






}
