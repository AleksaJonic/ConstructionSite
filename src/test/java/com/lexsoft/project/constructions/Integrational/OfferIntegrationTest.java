package com.lexsoft.project.constructions.Integrational;

import com.lexsoft.project.constructions.components.ClientCalls;
import com.lexsoft.project.constructions.model.OfferEnum;
import com.lexsoft.project.constructions.model.db.BidderDB;
import com.lexsoft.project.constructions.model.db.InvestorDB;
import com.lexsoft.project.constructions.model.db.TenderDB;
import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.model.dto.*;
import com.lexsoft.project.constructions.repository.OfferMapper;
import com.lexsoft.project.constructions.repository.UserMapper;
import com.lexsoft.project.constructions.service.BidderService;
import com.lexsoft.project.constructions.service.InvestorService;
import com.lexsoft.project.constructions.service.TenderService;
import com.lexsoft.project.constructions.utils.TestingData;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@AutoConfigureMybatis
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferIntegrationTest {

    InvestorDB investorDB;
    BidderDB bidderDB;
    TenderDB tender;
    List<OfferDto> offers;

    ClientCalls clientCalls;

    @LocalServerPort
    int port;

    @Autowired
    BidderService bidderService;
    @Autowired
    InvestorService investorService;
    @Autowired
    TenderService tenderService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    OfferMapper offerMapper;

    TestRestTemplate template = new TestRestTemplate();

    @Before
    public void prepareData() {
        TestingData testData = new TestingData();
        List<UserDB> dbUsers = testData.getDBUsers();
        UserDB investorUser = dbUsers.get(0);

        // create investor and user
        investorDB = testData.getDBInvestors().get(0);
        investorDB.setUsers(Arrays.asList(investorUser));
        investorDB = investorService.saveInvestor(investorDB);
        // create tender
        tender = testData.getDbTenders().get(0);
        tender.setActive(Boolean.TRUE);
        tender.setInvestor(investorDB);
        tender.setUser(investorDB.getUsers().get(0));
        tender = tenderService.saveTender(tender);
        //create bidder

        bidderDB = testData.getDBBidders().get(0);
        bidderDB.setUsers(Arrays.asList(dbUsers.get(1),dbUsers.get(2)));
        bidderDB = bidderService.saveBidder(bidderDB);

        offers = testData.getOffers();

        clientCalls = new ClientCalls();

    }

    @After
    public void removeData(){
        offerMapper.deleteOffersFromTender(tender.getId());
        tenderService.deleteTender(tender.getId());
        bidderService.deleteBidders(bidderDB.getId());
        investorService.deleteInvestor(investorDB.getId());
    }

    @Test
    public void placeOffersAndAcceptValidOne() {
        OfferDto oneOffer = offers.get(0);
        oneOffer.setTender(TenderDto.builder().id(tender.getId()).build());
        oneOffer.setUser(UserDto.builder().id(bidderDB.getUsers().get(0).getId()).build());

        OfferDto secondOffer = offers.get(1);
        secondOffer.setTender(TenderDto.builder().id(tender.getId()).build());
        secondOffer.setUser(UserDto.builder().id(bidderDB.getUsers().get(0).getId()).build());

        ResponseEntity<OfferDto> placeOfferEntity = clientCalls.placeOffer(port, template, oneOffer);
        Assert.assertEquals(Boolean.TRUE,placeOfferEntity.getStatusCode().is2xxSuccessful());
        OfferDto offer1Placed = placeOfferEntity.getBody();
        Assert.assertNotNull(offer1Placed.getId());
        Assert.assertNotNull(offer1Placed.getStatus());
        Assert.assertEquals(OfferEnum.PENDING.name(),offer1Placed.getStatus());

        ResponseEntity<OfferDto> placeOfferEntity2 = clientCalls.placeOffer(port, template, secondOffer);
        Assert.assertEquals(Boolean.TRUE,placeOfferEntity2.getStatusCode().is2xxSuccessful());
        OfferDto offer2Places = placeOfferEntity2.getBody();
        Assert.assertNotNull(offer2Places.getId());
        Assert.assertNotNull(offer2Places.getStatus());
        Assert.assertEquals(OfferEnum.PENDING.name(),offer2Places.getStatus());

        ResponseEntity<OfferDto[]> offersForBidder = clientCalls.getOffersForBidder(port, template, bidderDB.getId());
        Assert.assertEquals(Boolean.TRUE,placeOfferEntity2.getStatusCode().is2xxSuccessful());
        OfferDto[] bidderOffers = offersForBidder.getBody();
        Assert.assertEquals(2,bidderOffers.length);


        ResponseEntity<OfferDto[]> offersForTender = clientCalls.getOffersForTender(port, template, tender.getId());
        Assert.assertEquals(Boolean.TRUE,offersForTender.getStatusCode().is2xxSuccessful());
        OfferDto[] tenderOffers = offersForTender.getBody();
        Assert.assertEquals(2,tenderOffers.length);

        ResponseEntity<String> acceptOfferEntity = null;

        //try with invalid user
        acceptOfferEntity = clientCalls.acceptOffer(port, template, offer1Placed.getId(), AcceptOfferDto.builder()
                .acceptUserId(bidderDB.getUsers().get(0).getId())
                .build());
        Assert.assertEquals(Boolean.TRUE,acceptOfferEntity.getStatusCode().is4xxClientError());

        //try with proper user but with worse offer
        acceptOfferEntity = clientCalls.acceptOffer(port, template, offer1Placed.getId(), AcceptOfferDto.builder()
                .acceptUserId(investorDB.getUsers().get(0).getId())
                .build());
        Assert.assertEquals(Boolean.TRUE,acceptOfferEntity.getStatusCode().is4xxClientError());

        //try force with worse offer
        acceptOfferEntity = clientCalls.acceptOffer(port, template, offer1Placed.getId(), AcceptOfferDto.builder()
                .acceptUserId(investorDB.getUsers().get(0).getId())
                .forceAccept(Boolean.TRUE)
                .build());
        Assert.assertEquals(Boolean.TRUE,acceptOfferEntity.getStatusCode().is2xxSuccessful());


        offersForBidder = clientCalls.getOffersForBidder(port, template, bidderDB.getId());
        Assert.assertEquals(Boolean.TRUE,placeOfferEntity2.getStatusCode().is2xxSuccessful());
        OfferDto[] offers = offersForBidder.getBody();
        List<OfferDto> acceptedOffer = Arrays.asList(offers).stream().filter(e -> e.getAccept()).collect(Collectors.toList());
        List<OfferDto> notAccepted = Arrays.asList(offers).stream().filter(e -> !e.getAccept()).collect(Collectors.toList());
        Assert.assertEquals(1, acceptedOffer.size());
        Assert.assertEquals(1, notAccepted.size());
        Assert.assertEquals(OfferEnum.ACCEPTED.name(),acceptedOffer.get(0).getStatus());
        Assert.assertEquals(OfferEnum.REJECTED.name(),notAccepted.get(0).getStatus());
    }




}
