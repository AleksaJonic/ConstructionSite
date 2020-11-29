package com.lexsoft.project.constructions.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionEnum {

    PROPERTY_IS_MANDATORY(1002,"Property %s is mandatory.", HttpStatus.BAD_REQUEST),
    OBJECT_DOES_NOT_EXIST(1000, "Object %s identified by %s does not exist.", HttpStatus.BAD_REQUEST),
    OBJECT_ALLREADY_EXIST(1001, "Object %s identified by param %s with value %s allready exist.",HttpStatus.BAD_REQUEST);

    private Integer code;
    private String message;
    private HttpStatus statusCode;

    ExceptionEnum(Integer code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}
