package com.lexsoft.project.constructions.exception;


import com.lexsoft.project.constructions.exception.model.ErrorMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExceptionUtils {

    public static List<ErrorMessage> addError(ExceptionEnum error, List<ErrorMessage> errors, String... messagePart) {
        errors = Optional.ofNullable(errors).orElse(new ArrayList<>());
        errors.add(ErrorMessage.builder()
                .code(error.getCode())
                .message(String.format(error.getMessage(), messagePart))
                .build());
        return errors;
    }





}
