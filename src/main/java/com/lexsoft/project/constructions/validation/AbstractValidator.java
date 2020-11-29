package com.lexsoft.project.constructions.validation;

import com.lexsoft.project.constructions.exception.ExceptionEnum;
import com.lexsoft.project.constructions.exception.ExceptionUtils;
import com.lexsoft.project.constructions.exception.model.ErrorMessage;

import org.h2.util.StringUtils;

import java.util.List;

public abstract class AbstractValidator {

    protected void validateMandatory(String paramName, String param, List<ErrorMessage> errors){
        if(Boolean.TRUE.equals(StringUtils.isNullOrEmpty(param))){
            List<ErrorMessage> errorMessages = ExceptionUtils.addError(ExceptionEnum.PROPERTY_IS_MANDATORY, errors, paramName);
        }
    }

    protected void validateMandatory(String paramName, Object param, List<ErrorMessage> errors){
        if(param == null){
            List<ErrorMessage> errorMessages = ExceptionUtils.addError(ExceptionEnum.PROPERTY_IS_MANDATORY, errors, paramName);
        }
    }



}
