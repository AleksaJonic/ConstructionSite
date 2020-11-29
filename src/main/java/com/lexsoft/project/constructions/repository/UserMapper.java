package com.lexsoft.project.constructions.repository;

import com.lexsoft.project.constructions.model.db.UserDB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    void saveUser(@Param("user") UserDB userDB);
    UserDB findUserById(@Param("id") String id);
    void saveBatchUsers(@Param("users") List<UserDB> users);
    UserDB findUserByUsername(@Param("username") String username);
    public void deleteUserById(@Param("id") String id);
    public void deleteInvestorUsers(@Param("investorId") String investorId);
    public void deleteBidderUsers(@Param("bidderId") String bidderId);


}
