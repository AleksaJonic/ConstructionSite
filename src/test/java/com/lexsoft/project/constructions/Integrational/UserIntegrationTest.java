package com.lexsoft.project.constructions.Integrational;

import com.lexsoft.project.constructions.model.db.BidderDB;
import com.lexsoft.project.constructions.model.db.InvestorDB;
import com.lexsoft.project.constructions.model.dto.UserDto;
import com.lexsoft.project.constructions.service.BidderService;
import com.lexsoft.project.constructions.service.InvestorService;
import com.lexsoft.project.constructions.components.ClientCalls;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.lexsoft.project.constructions.utils.TestingData;

import java.util.List;
import java.util.UUID;


@RunWith(SpringRunner.class)
@AutoConfigureMybatis
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

    InvestorDB investorDB;
    BidderDB bidderDB;

    List<UserDto> userDtoList;
    ClientCalls clientCalls;
    TestRestTemplate template = new TestRestTemplate();

    @LocalServerPort
    int port;

    @Autowired
    InvestorService investorService;
    @Autowired
    BidderService bidderService;

    @Before
    public void prepareData(){
        TestingData testData = new TestingData();
        userDtoList = testData.getDtoUsers();
        clientCalls = new ClientCalls();

        List<InvestorDB> investorsDB = testData.getDBInvestors();
        investorDB = investorsDB.get(0);
        investorService.saveInvestor(investorDB);

        List<BidderDB> dbBidders = testData.getDBBidders();
        bidderDB = dbBidders.get(0);
        bidderService.saveBidder(bidderDB);
    }

    @After
    public void removeData(){
        investorService.deleteInvestor(investorDB.getId());
        bidderService.deleteBidders(bidderDB.getId());
    }

    @Test
    public void tryToSaveUserButDoesNotHaveInvestorOrBidder() {

        UserDto userDto = userDtoList.get(0);
        userDto.setInvestorId(UUID.randomUUID().toString());
        ResponseEntity<UserDto> user = clientCalls.createUser(port, template, userDto);
        Assert.assertEquals(HttpStatus.BAD_REQUEST,user.getStatusCode());

        userDto = userDtoList.get(1);
        userDto.setBidderId(UUID.randomUUID().toString());
        user = clientCalls.createUser(port, template, userDto);
        Assert.assertEquals(HttpStatus.BAD_REQUEST,user.getStatusCode());

    }

    @Test
    public void saveUsers() {

        UserDto user1 = userDtoList.get(0);
        user1.setInvestorId(investorDB.getId());
        ResponseEntity<UserDto> userEntity = clientCalls.createUser(port, template, user1);
        Assert.assertEquals(HttpStatus.OK,userEntity.getStatusCode());
        Assert.assertNotNull(userEntity.getBody());
        Assert.assertNotNull(userEntity.getBody().getId());
        user1.setId(userEntity.getBody().getId());

        UserDto user2 = userDtoList.get(1);
        user1.setBidderId(bidderDB.getId());
        userEntity = clientCalls.createUser(port, template, user2);
        Assert.assertEquals(HttpStatus.OK,userEntity.getStatusCode());
        Assert.assertNotNull(userEntity.getBody());
        Assert.assertNotNull(userEntity.getBody().getId());
        user2.setId(userEntity.getBody().getId());

        ResponseEntity<UserDto> oneUser = clientCalls.findOneUser(port, template, user1.getId());
        Assert.assertEquals(HttpStatus.OK, oneUser.getStatusCode());

        ResponseEntity<UserDto> secondUser = clientCalls.findOneUser(port, template, user2.getId());
        Assert.assertEquals(HttpStatus.OK, secondUser.getStatusCode());

    }





}
