package com.lexsoft.project.constructions.validation;

import com.lexsoft.project.constructions.exception.model.ErrorMessage;

import java.util.List;

public interface Validate<T> {

    public void validate(T body, List<ErrorMessage> errorList);


}
