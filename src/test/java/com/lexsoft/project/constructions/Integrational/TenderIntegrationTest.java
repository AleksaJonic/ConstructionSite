package com.lexsoft.project.constructions.Integrational;

import com.lexsoft.project.constructions.model.db.InvestorDB;
import com.lexsoft.project.constructions.model.dto.InvestorDto;

import com.lexsoft.project.constructions.model.dto.TenderDto;
import com.lexsoft.project.constructions.model.dto.UserDto;
import com.lexsoft.project.constructions.repository.UserMapper;
import com.lexsoft.project.constructions.service.InvestorService;
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

import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@AutoConfigureMybatis
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TenderIntegrationTest {

    InvestorDB investorDB;
    TenderDto tender;

    ClientCalls clientCalls;

    @LocalServerPort
    int port;

    @Autowired
    InvestorService investorService;
    @Autowired
    UserMapper userMapper;


    TestRestTemplate template = new TestRestTemplate();

    @Before
    public void prepareData() {
        TestingData testData = new TestingData();
        tender = testData.getDtoTenders().get(0);

        investorDB = testData.getDBInvestors().get(0);
        investorDB.setUsers(Arrays.asList(testData.getDBUsers().get(0)));
        investorDB = investorService.saveInvestor(investorDB);

        tender.setActive(Boolean.FALSE);
        tender.setInvestor(InvestorDto.builder()
                .id(investorDB.getId())
                .build());
        tender.setUser(UserDto.builder()
                .id(investorDB.getUsers().get(0).getId())
                .build());
        clientCalls = new ClientCalls();
    }

    @After
    public void removeData() {
        investorService.deleteInvestor(investorDB.getId());
    }

    @Test
    public void saveTender() {
        ResponseEntity<TenderDto> postTenderEntity = clientCalls.createTender(port, template, tender);
        Assert.assertTrue(postTenderEntity.getStatusCode().is2xxSuccessful());
        TenderDto tenderDto = postTenderEntity.getBody();
        Assert.assertNotNull(tenderDto.getId());

        ResponseEntity<TenderDto> getTenderEntity = clientCalls.findOneTender(port, template, tenderDto.getId());
        Assert.assertTrue(getTenderEntity.getStatusCode().is2xxSuccessful());
        Assert.assertNotNull(getTenderEntity.getBody());
    }


    @Test
    public void getAllTenders() {
        TenderDto tender2 = tender.toBuilder().name("some other name").
                description("description 2")
                .build();

        ResponseEntity<TenderDto> postTenderEntity = clientCalls.createTender(port, template, tender);
        Assert.assertTrue(postTenderEntity.getStatusCode().is2xxSuccessful());
        TenderDto tenderDto = postTenderEntity.getBody();
        Assert.assertNotNull(tenderDto.getId());

        ResponseEntity<TenderDto> postTenderEntity2 = clientCalls.createTender(port, template, tender2);
        Assert.assertTrue(postTenderEntity2.getStatusCode().is2xxSuccessful());
        TenderDto tenderDto2 = postTenderEntity2.getBody();
        Assert.assertNotNull(tenderDto2.getId());

        ResponseEntity<TenderDto[]> getMultipleTendersEntity = clientCalls.getMultipleTenders(port, template);
        Assert.assertTrue(getMultipleTendersEntity.getStatusCode().is2xxSuccessful());
        List<TenderDto> responseTenders = Arrays.asList(getMultipleTendersEntity.getBody());
        Assert.assertNotNull(responseTenders);
        Assert.assertFalse(responseTenders.isEmpty());
        Assert.assertEquals(2, responseTenders.size());

    }

    @Test
    public void activateTender() {

        tender.setActive(Boolean.FALSE);
        ResponseEntity<TenderDto> postTenderEntity = clientCalls.createTender(port, template, tender);
        Assert.assertTrue(postTenderEntity.getStatusCode().is2xxSuccessful());
        TenderDto tenderDto = postTenderEntity.getBody();
        Assert.assertNotNull(tenderDto.getId());

        ResponseEntity<TenderDto> getTenderEntity = clientCalls.findOneTender(port, template, tenderDto.getId());
        Assert.assertTrue(getTenderEntity.getStatusCode().is2xxSuccessful());
        Assert.assertNotNull(getTenderEntity.getBody());
        TenderDto getTender = getTenderEntity.getBody();
        Assert.assertEquals(Boolean.FALSE, getTender.getActive());

        ResponseEntity<TenderDto> activateTenderEntity = clientCalls.activateTender(port, template, getTender.getId());

        Assert.assertTrue(activateTenderEntity.getStatusCode().is2xxSuccessful());
        Assert.assertNotNull(activateTenderEntity.getBody());
        TenderDto activateTenderDto = activateTenderEntity.getBody();
        Assert.assertEquals(Boolean.TRUE, activateTenderDto.getActive());

    }









}
