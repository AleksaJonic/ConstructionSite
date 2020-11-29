package com.lexsoft.project.constructions.validation.impl;

import com.lexsoft.project.constructions.exception.model.ErrorMessage;
import com.lexsoft.project.constructions.exception.types.InternalWebException;
import com.lexsoft.project.constructions.model.dto.UserDto;
import com.lexsoft.project.constructions.validation.AbstractValidator;
import com.lexsoft.project.constructions.validation.Validate;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserValidator extends AbstractValidator implements Validate<UserDto> {

    @Override
    public void validate(UserDto body, List<ErrorMessage> errorList) {
        Boolean hasParentValidator = errorList != null ? Boolean.TRUE : Boolean.FALSE;
        errorList = Optional.ofNullable(errorList).orElse(new ArrayList<>());

        validateMandatory("username",body.getUsername(),errorList);
        validateMandatory("password",body.getPassword(),errorList);

        if(!hasParentValidator && !errorList.isEmpty()){
            throw new InternalWebException(HttpStatus.BAD_REQUEST,errorList);
        }
    }


}
