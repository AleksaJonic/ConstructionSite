package com.lexsoft.project.constructions.validation.impl;

import com.lexsoft.project.constructions.exception.model.ErrorMessage;
import com.lexsoft.project.constructions.exception.types.InternalWebException;
import com.lexsoft.project.constructions.model.dto.AcceptOfferDto;
import com.lexsoft.project.constructions.validation.AbstractValidator;
import com.lexsoft.project.constructions.validation.Validate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AcceptOfferValidator extends AbstractValidator implements Validate<AcceptOfferDto> {

    @Override
    public void validate(AcceptOfferDto body, List<ErrorMessage> errorList) {
        List<ErrorMessage> finalErrorList = Optional.ofNullable(errorList).orElse(new ArrayList<>());
        validateMandatory("acceptUserId",body.getAcceptUserId(),finalErrorList);
        if(!finalErrorList.isEmpty()){
            throw new InternalWebException(HttpStatus.BAD_REQUEST,finalErrorList);
        }
    }



}
