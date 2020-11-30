package com.lexsoft.project.constructions.Integrational;

import com.lexsoft.project.constructions.model.dto.BidderDto;

import com.lexsoft.project.constructions.service.BidderService;
import com.lexsoft.project.constructions.utils.TestingData;

import com.lexsoft.project.constructions.components.ClientCalls;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@AutoConfigureMybatis
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BidderIntegrationTest {

    List<BidderDto> bidders;

    ClientCalls clientCalls;

    @LocalServerPort
    int port;

    @Autowired
    BidderService bidderService;

    TestRestTemplate template = new TestRestTemplate();

    @Before
    public void prepareData(){
        TestingData testData = new TestingData();
        bidders = testData.getDtoBidders();
        clientCalls = new ClientCalls();
    }

    @After
    public void removeData(){
        bidders.forEach(bidder -> bidderService.deleteBidders(bidder.getId()));
    }


    @Test
    public void saveBidder() {
        bidders.forEach(bidderDto -> {

            ResponseEntity<BidderDto> bidderEntity = clientCalls.createBidder(port, template, bidderDto);
            Assert.assertTrue(bidderEntity.getStatusCode().is2xxSuccessful());
            Assert.assertNotNull(bidderEntity.getBody().getId());
            BidderDto bidder = bidderEntity.getBody();
            bidder.getUsers().forEach(u -> Assert.assertNotNull(u.getId()));

            ResponseEntity<BidderDto> oneInvestorEntity = clientCalls.findOneBidder(port, template, bidder.getId());
            Assert.assertTrue(oneInvestorEntity.getStatusCode().is2xxSuccessful());
            Assert.assertNotNull(oneInvestorEntity.getBody());
        });
    }

    @Test
    public void findBidders() {
        ResponseEntity<BidderDto[]> biddersEntity = clientCalls.findBidders(port, template);
        Assert.assertTrue(biddersEntity.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(bidders.size(),biddersEntity.getBody().length);
    }


}
