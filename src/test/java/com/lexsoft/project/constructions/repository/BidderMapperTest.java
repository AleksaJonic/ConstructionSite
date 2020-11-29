package com.lexsoft.project.constructions.repository;

import com.lexsoft.project.constructions.model.db.BidderDB;
import com.lexsoft.project.constructions.model.db.UserDB;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.lexsoft.project.constructions.utils.TestingData;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@RunWith(SpringRunner.class)
@MybatisTest
public class BidderMapperTest {

    List<BidderDB> bidders;
    List<UserDB> users;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BidderMapper bidderMapper;


    @Before
    public void prepareData() {
        TestingData testingData = new TestingData();
        users = testingData.getDBUsers();
        bidders = testingData.getDBBidders();
    }

    @After
    public void removeData() {
        bidders.forEach(bidderDB -> {
            userMapper.deleteBidderUsers(bidderDB.getId());
            bidderMapper.deleteBidderById(bidderDB.getId());
        });
    }

    @Test
    public void saveAndFindBidder(){
        bidders.forEach(bidderDB -> {
            bidderMapper.saveBidder(bidderDB);
        });
        Map<String,List<BidderDB>> investorsByIds = bidders.stream().collect(groupingBy(BidderDB::getId));

        investorsByIds.forEach((k,v) -> {
            BidderDB bidderById = bidderMapper.findBidderById(k);
            Assert.assertNotNull(bidderById);
            Assert.assertEquals(bidderById.getName(),v.get(0).getName());
            Assert.assertEquals(bidderById.getHasWorkingReference(),v.get(0).getHasWorkingReference());
        });
    }

    @Test
    public void saveAndFindBidderWithUsers(){
        BidderDB bidderDB = bidders.get(0);
        bidderMapper.saveBidder(bidderDB);
        users.forEach(u -> {
            u.setBidderId(bidderDB.getId());
            userMapper.saveUser(u);
        });

        BidderDB investorFromDb = bidderMapper.findBidderById(bidderDB.getId());
        investorFromDb.getUsers().size();

    }


}
