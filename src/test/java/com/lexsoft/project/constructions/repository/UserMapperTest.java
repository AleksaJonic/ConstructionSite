package com.lexsoft.project.constructions.repository;

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
public class UserMapperTest {

    List<UserDB> users;
    @Autowired
    private UserMapper userMapper;

    @Before
    public void prepareData() {
        users = new TestingData().getDBUsers();
    }

    @After
    public void removeData(){
        users.forEach(u -> userMapper.deleteUserById(u.getId()));
    }

    @Test
    public void saveAndFindUser(){
        users.forEach(user -> {
            userMapper.saveUser(user);
        });
        Map<String,List<UserDB>> usersByIds = users.stream().collect(groupingBy(UserDB::getId));
        usersByIds.forEach((k,v) -> {
            UserDB userById = userMapper.findUserById(k);
            Assert.assertNotNull(userById);
            Assert.assertEquals(userById.getUsername(),v.get(0).getUsername());
            Assert.assertEquals(userById.getPassword(),v.get(0).getPassword());
        });
    }

    @Test
    public void insertUsersInBatch(){
        userMapper.saveBatchUsers(users);
        Map<String,List<UserDB>> usersByIds = users.stream().collect(groupingBy(UserDB::getId));
        usersByIds.forEach((k,v) -> {
            UserDB userById = userMapper.findUserById(k);
            Assert.assertNotNull(userById);
            Assert.assertEquals(userById.getUsername(),v.get(0).getUsername());
            Assert.assertEquals(userById.getPassword(),v.get(0).getPassword());
        });
    }

}
