package com.lexsoft.project.constructions.validation;

import com.lexsoft.project.constructions.exception.ExceptionEnum;
import com.lexsoft.project.constructions.exception.ExceptionUtils;
import com.lexsoft.project.constructions.exception.model.ErrorMessage;

import org.h2.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractValidator {

    protected void validateMandatory(String paramName, String param, List<ErrorMessage> errors){
        if(Boolean.TRUE.equals(StringUtils.isNullOrEmpty(param))){
            ExceptionUtils.addError(ExceptionEnum.PROPERTY_IS_MANDATORY, errors, paramName);
        }
    }

    protected void validateMandatory(String paramName, Object param, List<ErrorMessage> errors){
        if(param == null){
           ExceptionUtils.addError(ExceptionEnum.PROPERTY_IS_MANDATORY, errors, paramName);
        }
    }

    public void validateOneOf(List<String> availableElements, String paramName, List<ErrorMessage> errors, String... elements) {
        List<String> elemsInArray = Arrays.asList(elements);
        List<String> common = new ArrayList<String>(elemsInArray);
        common.retainAll(availableElements);
        if (common.size() > 1 || common.size() < 1) {
            ExceptionUtils.addError(ExceptionEnum.OBJECT_CONTAIN_ONE_VALUE, errors, paramName,
                    String.join(",", availableElements));
        }
    }

}
