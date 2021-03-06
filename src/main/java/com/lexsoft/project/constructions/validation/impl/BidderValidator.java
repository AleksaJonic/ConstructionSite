package com.lexsoft.project.constructions.validation.impl;

import com.lexsoft.project.constructions.exception.model.ErrorMessage;
import com.lexsoft.project.constructions.exception.types.InternalWebException;
import com.lexsoft.project.constructions.model.dto.BidderDto;
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
public class BidderValidator extends AbstractValidator implements Validate<BidderDto> {

    @Override
    public void validate(BidderDto body, List<ErrorMessage> errorList) {
        Boolean hasParentValidator = errorList != null ? Boolean.TRUE : Boolean.FALSE;
        List<ErrorMessage> finalErrorList = Optional.ofNullable(errorList).orElse(new ArrayList<>());

        validateMandatory("name",body.getName(),finalErrorList);
        validateMandatory("workingReference", body.getWorkingReference(),finalErrorList);

        Optional.ofNullable(body.getUsers())
                .filter(users -> !users.isEmpty())
                .ifPresent(users -> users.forEach(u -> {
                    validateMandatory("username", u.getUsername(),finalErrorList);
                    validateMandatory("password",u.getPassword(), finalErrorList);
                }));

        if(!hasParentValidator && !finalErrorList.isEmpty()){
            throw new InternalWebException(HttpStatus.BAD_REQUEST,finalErrorList);
        }

    }
}
