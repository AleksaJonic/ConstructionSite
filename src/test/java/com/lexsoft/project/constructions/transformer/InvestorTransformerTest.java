package com.lexsoft.project.constructions.transformer;

import com.lexsoft.project.constructions.model.db.InvestorDB;
import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.model.dto.InvestorDto;
import com.lexsoft.project.constructions.transformer.impl.InvestorTransformer;
import com.lexsoft.project.constructions.transformer.impl.UserTransformer;
import com.lexsoft.project.constructions.utils.TestingData;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
public class InvestorTransformerTest {

    InvestorDB investorDB;
    List<UserDB> userdDB;
    InvestorDto investorDto;

    @Autowired
    InvestorTransformer investorTransformer;


    @TestConfiguration
    static class InvestorConversionTestConfiguration {
        @Bean
        public InvestorTransformer investorTransformer() {
            return new InvestorTransformer();
        }
        @Bean
        public UserTransformer userTransformer(){return new UserTransformer();}
        @Bean
        public TestingData testingData() {
            return new TestingData();
        }

    }

    @Before
    public void prepareData(){
        TestingData td = new TestingData();
        investorDB = td.getDBInvestors().get(0);
        investorDto = td.getDtoInvestors().get(0);
        userdDB = td.getDBUsers();
    }

    @Test
    public void transformFromDtoToDBModelTest(){
        InvestorDB transformed = investorTransformer.transform(investorDto);
        Assert.assertEquals(investorDto.getName(),transformed.getName());
        Assert.assertEquals(investorDto.getDescription(),transformed.getDescription());
        Assert.assertEquals(investorDto.getId(),transformed.getId());
        Assert.assertEquals(investorDto.getUsers().size(),transformed.getUsers().size());
    }

    @Test
    public void transformFromDBToDtoModelTest(){
        InvestorDto transformed = investorTransformer.transformBackwards(investorDB);
        Assert.assertEquals(investorDB.getName(),transformed.getName());
        Assert.assertEquals(investorDB.getDescription(),transformed.getDescription());
        Assert.assertEquals(investorDB.getId(),transformed.getId());
    }







}



