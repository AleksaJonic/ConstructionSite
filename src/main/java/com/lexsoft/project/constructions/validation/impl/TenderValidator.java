package com.lexsoft.project.constructions.validation.impl;

import com.lexsoft.project.constructions.exception.model.ErrorMessage;
import com.lexsoft.project.constructions.exception.types.InternalWebException;
import com.lexsoft.project.constructions.model.dto.TenderDto;
import com.lexsoft.project.constructions.validation.AbstractValidator;
import com.lexsoft.project.constructions.validation.Validate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TenderValidator extends AbstractValidator implements Validate<TenderDto> {

    @Override
    public void validate(TenderDto body, List<ErrorMessage> errorList) {

        List<ErrorMessage> finalErrorList = Optional.ofNullable(errorList).orElse(new ArrayList<>());
        validateMandatory("name", body.getName(),finalErrorList);
        validateMandatory("description",body.getDescription(), finalErrorList);
        validateMandatory("user",body.getUser(),finalErrorList);
        validateMandatory("investor",body.getInvestor(),finalErrorList);

        Optional.ofNullable(body.getUser()).ifPresent(udto -> {
            validateMandatory("user.id",udto.getId(),errorList);
        });
        Optional.ofNullable(body.getInvestor()).ifPresent(idto -> {
            validateMandatory("investor.id",idto.getId(),errorList);
        });
        if(!finalErrorList.isEmpty()){
            throw new InternalWebException(HttpStatus.BAD_REQUEST,finalErrorList);
        }

    }
}
