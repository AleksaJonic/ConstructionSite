package com.lexsoft.project.constructions.transformer;

import com.lexsoft.project.constructions.model.db.TenderDB;
import com.lexsoft.project.constructions.model.dto.TenderDto;
import com.lexsoft.project.constructions.transformer.impl.InvestorTransformer;
import com.lexsoft.project.constructions.transformer.impl.TenderTransformer;
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


@RunWith(SpringRunner.class)
public class TenderTransformerTest {

    TenderDB tenderDB;
    TenderDto tenderDto;

    @Autowired
    TenderTransformer tenderTransformer;


    @TestConfiguration
    static class InvestorConversionTestConfiguration {
        @Bean
        public InvestorTransformer investorTransformer() {
            return new InvestorTransformer();
        }
        @Bean
        public TenderTransformer tenderTransformer(){return new TenderTransformer();}
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
        tenderDto = td.getDtoTenders().get(0);
        tenderDB = td.getDbTenders().get(0);

    }

    @Test
    public void transformFromDtoToDBModelTest(){
        TenderDB transformed = tenderTransformer.transform(tenderDto);
        Assert.assertEquals(tenderDto.getName(),transformed.getName());
        Assert.assertEquals(tenderDto.getDescription(),transformed.getDescription());
        Assert.assertEquals(tenderDto.getId(),transformed.getId());
        Assert.assertEquals(tenderDto.getUser().getId(),transformed.getUser().getId());
        Assert.assertEquals(tenderDto.getInvestor().getId(),transformed.getInvestor().getId());
    }

    @Test
    public void transformFromDBToDtoModelTest(){
        TenderDto transformed = tenderTransformer.transformBackwards(tenderDB);
        Assert.assertEquals(tenderDB.getName(),transformed.getName());
        Assert.assertEquals(tenderDB.getDescription(),transformed.getDescription());
        Assert.assertEquals(tenderDB.getId(),transformed.getId());
        Assert.assertEquals(tenderDB.getUser().getId(),transformed.getUser().getId());
        Assert.assertEquals(tenderDB.getInvestor().getId(),transformed.getInvestor().getId());
    }







}



