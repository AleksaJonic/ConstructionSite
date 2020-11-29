package com.lexsoft.project.constructions.Integrational;

import com.lexsoft.project.constructions.model.dto.InvestorDto;

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


import java.util.List;


@RunWith(SpringRunner.class)
@AutoConfigureMybatis
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InvestorIntegrationTest {

    List<InvestorDto> investors;

    ClientCalls clientCalls;

    @LocalServerPort
    int port;

    @Autowired
    InvestorService investorService;
    @Autowired
    UserMapper userMapper;

    TestRestTemplate template = new TestRestTemplate();

    @Before
    public void prepareData(){
        TestingData testData = new TestingData();
        investors = testData.getDtoInvestors();
        clientCalls = new ClientCalls();
    }

    @After
    public void removeData(){
        investors.forEach(inv -> investorService.deleteInvestor(inv.getId()));
    }

    @Test
    public void saveInvestor() {
        investors.forEach(investorDto -> {
            ResponseEntity<InvestorDto> investorEntity = clientCalls.createInvestor(port, template, investorDto);
            Assert.assertTrue(investorEntity.getStatusCode().is2xxSuccessful());
            Assert.assertNotNull(investorEntity.getBody().getId());
            InvestorDto investor = investorEntity.getBody();
            investor.getUsers().forEach(u -> {
                Assert.assertNotNull(u.getId());
            });

            ResponseEntity<InvestorDto> oneInvestorEntity = clientCalls.findOneInvestor(port, template, investorEntity.getBody().getId());
            Assert.assertTrue(oneInvestorEntity.getStatusCode().is2xxSuccessful());
            Assert.assertNotNull(oneInvestorEntity.getBody());
        });
    }

    @Test
    public void findMultipleInvestors() {
        ResponseEntity<InvestorDto[]> investorsEntity = clientCalls.findInvestors(port, template);
        Assert.assertTrue(investorsEntity.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(investors.size(),investorsEntity.getBody().length);
    }


}
