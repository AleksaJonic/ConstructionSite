package com.lexsoft.project.constructions.validation.impl;

import com.lexsoft.project.constructions.exception.model.ErrorMessage;
import com.lexsoft.project.constructions.exception.types.InternalWebException;
import com.lexsoft.project.constructions.model.dto.OfferDto;
import com.lexsoft.project.constructions.validation.AbstractValidator;
import com.lexsoft.project.constructions.validation.Validate;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OfferValidator extends AbstractValidator implements Validate<OfferDto> {

    @Override
    public void validate(OfferDto body, List<ErrorMessage> errorList) {

        List<ErrorMessage> finalErrorList = Optional.ofNullable(errorList).orElse(new ArrayList<>());

        validateMandatory("amount", body.getAmount(),finalErrorList);
        validateMandatory("description",body.getDescription(), finalErrorList);
        validateMandatory("user",body.getUser(),finalErrorList);
        validateMandatory("tender",body.getTender(),finalErrorList);

        Optional.ofNullable(body.getUser()).map(user -> user.getId())
                .ifPresent(userID -> validateMandatory("user.id",userID,finalErrorList));
        Optional.ofNullable(body.getTender()).map(tender -> tender.getId())
                .ifPresent(tenderId -> validateMandatory("tenderId.id",tenderId,finalErrorList));

        if(!finalErrorList.isEmpty()){
            throw new InternalWebException(HttpStatus.BAD_REQUEST,finalErrorList);
        }



    }



}
