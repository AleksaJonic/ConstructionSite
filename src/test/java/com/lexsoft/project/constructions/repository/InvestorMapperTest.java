package com.lexsoft.project.constructions.repository;

import com.lexsoft.project.constructions.model.db.InvestorDB;
import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.utils.TestingData;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@RunWith(SpringRunner.class)
@MybatisTest
public class InvestorMapperTest {

    List<InvestorDB> investors;
    List<UserDB> users;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private InvestorMapper investorMapper;

    @Before
    public void prepareData() {
        TestingData testingData = new TestingData();
        users = testingData.getDBUsers();
        investors = testingData.getDBInvestors();
    }

    @After
    public void removeData() {
        investors.forEach(investorDB -> {
            userMapper.deleteInvestorUsers(investorDB.getId());
            investorMapper.deleteInvestorById(investorDB.getId());
        });
    }

    @Test
    public void saveAndFindInvestor(){
        investors.forEach(investor -> {
            investorMapper.saveInvestor(investor);
        });
        Map<String,List<InvestorDB>> investorsByIds = investors.stream().collect(groupingBy(InvestorDB::getId));

        investorsByIds.forEach((k,v) -> {
            InvestorDB investorById = investorMapper.findInvestorById(k);
            Assert.assertNotNull(investorById);
            Assert.assertEquals(investorById.getName(),v.get(0).getName());
            Assert.assertEquals(investorById.getDescription(),v.get(0).getDescription());
        });
    }

    @Test
    public void saveAndFindInvestorWithUser(){
        InvestorDB investorDB = investors.get(0);
        investorMapper.saveInvestor(investorDB);
        users.forEach(u -> {
            u.setInvestorId(investorDB.getId());
            userMapper.saveUser(u);
        });

        InvestorDB investorFromDb = investorMapper.findInvestorById(investorDB.getId());
        investorFromDb.getUsers().size();

    }


}
