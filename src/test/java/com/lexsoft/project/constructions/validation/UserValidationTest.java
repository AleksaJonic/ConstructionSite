package com.lexsoft.project.constructions.validation;

import com.lexsoft.project.constructions.exception.ExceptionEnum;
import com.lexsoft.project.constructions.exception.model.ErrorMessage;
import com.lexsoft.project.constructions.exception.types.InternalWebException;
import com.lexsoft.project.constructions.model.dto.UserDto;
import com.lexsoft.project.constructions.utils.TestingData;
import com.lexsoft.project.constructions.validation.impl.UserValidator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;


@RunWith(SpringRunner.class)
public class UserValidationTest {

    UserDto userDto;

    @Autowired
    UserValidator userValidator;

    @Autowired
    TestingData testingData;

    @TestConfiguration
    static class InvestorConversionTestConfiguration {
        @Bean
        public UserValidator userValidator() {
            return new UserValidator();
        }

        @Bean
        public TestingData testingData() {
            return new TestingData();
        }
    }

    @Before
    public void prepareData(){
        userDto = testingData.getDtoUsers().get(0);
    }

    @Test
    public void validateUserWithoutUsername(){
        userDto.setInvestorId(UUID.randomUUID().toString());
        userDto.setUsername(null);
        try {
            userValidator.validate(userDto, null);
        }catch (InternalWebException ex){
            Assert.assertEquals(1,ex.getErrors().size());
            ErrorMessage errorMessage = ex.getErrors().get(0);
            Assert.assertEquals(ExceptionEnum.PROPERTY_IS_MANDATORY.getCode(),
                    errorMessage.getCode());
        }
    }

    @Test
    public void validateWithoutUsernameAndPassword(){
        userDto.setInvestorId(UUID.randomUUID().toString());
        userDto.setUsername(null);
        userDto.setPassword(null);
        try {
            userValidator.validate(userDto, null);
        }catch (InternalWebException ex){
            Assert.assertEquals(2,ex.getErrors().size());
            ex.getErrors().forEach(err -> Assert.assertEquals(ExceptionEnum.PROPERTY_IS_MANDATORY.getCode(),
                    err.getCode()));
        }
    }

    @Test
    public void validateIfInvestorAndBidderIDsArePresent(){
        userDto.setBidderId("someid");
        userDto.setInvestorId("someOtherId");
        try {
            userValidator.validate(userDto, null);
        }catch (InternalWebException ex){
            Assert.assertEquals(1,ex.getErrors().size());
            ex.getErrors().forEach(err -> Assert.assertEquals(ExceptionEnum.OBJECT_CONTAIN_ONE_VALUE.getCode(),
                    err.getCode()));
        }
    }

}
