package com.lexsoft.project.constructions.repository;

import com.lexsoft.project.constructions.model.db.InvestorDB;
import com.lexsoft.project.constructions.model.db.TenderDB;
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
import java.util.UUID;

import static java.util.stream.Collectors.groupingBy;

@RunWith(SpringRunner.class)
@MybatisTest
public class TenderMapperTest {

    InvestorDB investor;
    List<UserDB> users;
    TenderDB tender;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private InvestorMapper investorMapper;
    @Autowired
    private TenderMapper tenderMapper;


    @Before
    public void prepareData() {
        TestingData testingData = new TestingData();
        users = testingData.getDBUsers();

        investor = testingData.getDBInvestors().get(0);

        investor.setId(UUID.randomUUID().toString());
        tender = testingData.getDbTenders().get(0);
        tender.setId(UUID.randomUUID().toString());
        //add investors
        tender.setInvestor(investor);
        //add users
        users.get(0).setInvestorId(investor.getId());

        tender.setUser(users.get(0));
        tender.setActive(Boolean.FALSE);

        investorMapper.saveInvestor(investor);
        userMapper.saveUser(tender.getUser());
        tenderMapper.saveTender(tender);

    }

    @After
    public void removeData() {

            tenderMapper.deleteTender(null, investor.getId(),null);
            userMapper.deleteInvestorUsers(investor.getId());
            investorMapper.deleteInvestorById(investor.getId());

    }

    @Test
    public void saveAndFindTender() {
        //tender is saved in prepareData
        TenderDB tenderById = tenderMapper.findTenderById(tender.getId());
        Assert.assertNotNull(tenderById);
        Assert.assertEquals(tender.getName(), tenderById.getName());
        Assert.assertEquals(tender.getDescription(), tenderById.getDescription());
        Assert.assertEquals(tender.getActive(), tenderById.getActive());
        Assert.assertEquals(tender.getInvestor().getId(), tenderById.getInvestor().getId());
        Assert.assertEquals(tender.getUser().getId(), tenderById.getUser().getId());

    }

    @Test
    public void deleteTenders(){
        tenderMapper.deleteTender(tender.getId(),null,null);
        TenderDB tenderById = tenderMapper.findTenderById(tender.getId());
        Assert.assertNull(tenderById);
    }

    @Test
    public void activateTender(){
        tenderMapper.activateTender(tender.getId());
        TenderDB tender = tenderMapper.findTenderById(this.tender.getId());
        Assert.assertEquals(Boolean.TRUE, tender.getActive());
    }




}
