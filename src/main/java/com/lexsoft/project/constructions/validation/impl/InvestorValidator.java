package com.lexsoft.project.constructions.validation.impl;

import com.lexsoft.project.constructions.exception.model.ErrorMessage;
import com.lexsoft.project.constructions.exception.types.InternalWebException;
import com.lexsoft.project.constructions.model.dto.InvestorDto;
import com.lexsoft.project.constructions.model.dto.UserDto;
import com.lexsoft.project.constructions.validation.AbstractValidator;
import com.lexsoft.project.constructions.validation.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InvestorValidator extends AbstractValidator implements Validate<InvestorDto> {

    @Autowired
    Validate<UserDto> userValidator;

    @Override
    public void validate(InvestorDto body, List<ErrorMessage> errorList) {
        Boolean hasParentValidator = errorList != null ? Boolean.TRUE : Boolean.FALSE;
        List<ErrorMessage> finalErrorList = Optional.ofNullable(errorList).orElse(new ArrayList<>());

        validateMandatory("name",body.getName(),errorList);
        validateMandatory("description", body.getDescription(),errorList);

        Optional.ofNullable(body.getUsers())
                .filter(users -> !users.isEmpty())
                .ifPresent(users -> users.forEach(u -> userValidator.validate(u, finalErrorList)));

        if(!hasParentValidator && !finalErrorList.isEmpty()){
            throw new InternalWebException(HttpStatus.BAD_REQUEST,finalErrorList);
        }

    }
}
