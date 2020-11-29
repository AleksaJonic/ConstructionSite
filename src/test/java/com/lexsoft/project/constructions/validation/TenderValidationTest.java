package com.lexsoft.project.constructions.validation;

import com.lexsoft.project.constructions.exception.model.ErrorMessage;
import com.lexsoft.project.constructions.exception.types.InternalWebException;
import com.lexsoft.project.constructions.model.dto.TenderDto;
import com.lexsoft.project.constructions.utils.TestingData;
import com.lexsoft.project.constructions.validation.impl.TenderValidator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class TenderValidationTest {

    TenderDto tenderDto;

    @Autowired
    TenderValidator tenderValidator;

    @Autowired
    TestingData testingData;

    @TestConfiguration
    static class InvestorConversionTestConfiguration {
        @Bean
        public TenderValidator investorTransformer() {
            return new TenderValidator();
        }

        @Bean
        public TestingData testingData() {
            return new TestingData();
        }
    }

    @Before
    public void prepareData(){
        tenderDto = testingData.getDtoTenders().get(0);
    }

    @Test
    public void validateTenderWithoutName(){
        tenderDto.setName(null);
        try {
            tenderValidator.validate(tenderDto, null);
        }catch (InternalWebException ex){
            Assert.assertEquals(1,ex.getErrors().size());
            ErrorMessage errorMessage = ex.getErrors().get(0);
            Assert.assertEquals(Integer.valueOf(1002),errorMessage.getCode());
        }
    }

    @Test
    public void validateWithoutNameAndDescription(){
        tenderDto.setName(null);
        tenderDto.setDescription(null);
        try {
            tenderValidator.validate(tenderDto, null);
        }catch (InternalWebException ex){
            Assert.assertEquals(2,ex.getErrors().size());
            ex.getErrors().forEach(err -> Assert.assertEquals(Integer.valueOf(1002),err.getCode()));
        }
    }

    @Test
    public void validateWithoutNameAndDescriptionAndUser(){
        tenderDto.setName(null);
        tenderDto.setDescription(null);
        tenderDto.setUser(null);
        try {
            tenderValidator.validate(tenderDto, null);
        }catch (InternalWebException ex){
            Assert.assertEquals(3,ex.getErrors().size());
            ex.getErrors().forEach(err -> Assert.assertEquals(Integer.valueOf(1002),err.getCode()));
        }
    }

    @Test
    public void validateWithoutAllMandatoryParams(){
        tenderDto.setName(null);
        tenderDto.setDescription(null);
        tenderDto.setUser(null);
        tenderDto.setInvestor(null);
        try {
            tenderValidator.validate(tenderDto, null);
        }catch (InternalWebException ex){
            Assert.assertEquals(4,ex.getErrors().size());
            ex.getErrors().forEach(err -> Assert.assertEquals(Integer.valueOf(1002),err.getCode()));
        }
    }
}
