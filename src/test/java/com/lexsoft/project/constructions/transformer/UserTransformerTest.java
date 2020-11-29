package com.lexsoft.project.constructions.transformer;

import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.model.dto.UserDto;
import com.lexsoft.project.constructions.transformer.impl.UserTransformer;
import com.lexsoft.project.constructions.utils.HashingUtils;
import com.lexsoft.project.constructions.utils.TestingData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class UserTransformerTest {

    UserDB userDB;
    UserDto userDto;

    @Autowired
    UserTransformer userTransformer;

    @TestConfiguration
    static class BeerConversionTestConfiguration {

        @Bean
        public UserTransformer userTransformer() {
            return new UserTransformer();
        }

        @Bean
        public TestingData testingData() {
            return new TestingData();
        }
    }


    @Before
    public void prepareData(){
        TestingData td = new TestingData();
        userDB = td.getDBUsers().get(0);
        userDto = td.getDtoUsers().get(0);
    }

    @Test
    public void transformFromDtoToDBModelTest(){
        UserDB transformed = userTransformer.transform(userDto);
        Assert.assertEquals(userDto.getUsername(),transformed.getUsername());
        Assert.assertEquals(userDto.getBidderId(),transformed.getBidderId());
        Assert.assertEquals(userDto.getInvestorId(),transformed.getInvestorId());
        Assert.assertEquals(HashingUtils.hashPassword(userDto.getPassword()),transformed.getPassword());
    }

    @Test
    public void transformFromDBToDtoModelTest(){
        UserDto transformed = userTransformer.transformBackwards(userDB);
        Assert.assertEquals(userDB.getUsername(),transformed.getUsername());
        Assert.assertEquals(userDB.getBidderId(),transformed.getBidderId());
        Assert.assertEquals(userDB.getInvestorId(),transformed.getInvestorId());
        Assert.assertEquals(null,transformed.getPassword());
    }




}



