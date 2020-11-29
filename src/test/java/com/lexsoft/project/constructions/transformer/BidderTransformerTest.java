package com.lexsoft.project.constructions.transformer;

import com.lexsoft.project.constructions.model.db.BidderDB;
import com.lexsoft.project.constructions.model.db.InvestorDB;
import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.model.dto.BidderDto;
import com.lexsoft.project.constructions.model.dto.InvestorDto;
import com.lexsoft.project.constructions.transformer.impl.BidderTransformer;
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
public class BidderTransformerTest {

    BidderDB bidderDB;
    List<UserDB> userdDB;
    BidderDto bidderDto;

    @Autowired
    BidderTransformer bidderTransformer;


    @TestConfiguration
    static class InvestorConversionTestConfiguration {
        @Bean
        public BidderTransformer investorTransformer() {
            return new BidderTransformer();
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
        bidderDB = td.getDBBidders().get(0);
        bidderDto = td.getDtoBidders().get(0);
        userdDB = td.getDBUsers();
    }

    @Test
    public void transformFromDtoToDBModelTest(){
        BidderDB transformed = bidderTransformer.transform(bidderDto);
        Assert.assertEquals(bidderDto.getName(),transformed.getName());
        Assert.assertEquals(bidderDto.getWorkingReference(),transformed.getHasWorkingReference());
        Assert.assertEquals(bidderDto.getId(),transformed.getId());
        Assert.assertEquals(bidderDto.getUsers().size(),transformed.getUsers().size());
    }

    @Test
    public void transformFromDBToDtoModelTest(){
        BidderDto transformed = bidderTransformer.transformBackwards(bidderDB);
        Assert.assertEquals(bidderDB.getName(),transformed.getName());
        Assert.assertEquals(bidderDB.getHasWorkingReference(),transformed.getWorkingReference());
        Assert.assertEquals(bidderDB.getId(),transformed.getId());
    }







}



