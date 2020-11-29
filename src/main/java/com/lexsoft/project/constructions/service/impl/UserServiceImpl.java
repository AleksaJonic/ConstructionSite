package com.lexsoft.project.constructions.service.impl;

import com.lexsoft.project.constructions.exception.ExceptionEnum;
import com.lexsoft.project.constructions.exception.ExceptionUtils;
import com.lexsoft.project.constructions.exception.types.InternalWebException;
import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.repository.BidderMapper;
import com.lexsoft.project.constructions.repository.InvestorMapper;
import com.lexsoft.project.constructions.repository.UserMapper;
import com.lexsoft.project.constructions.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    InvestorMapper investorMapper;
    @Autowired
    BidderMapper bidderMapper;

    @Override
    @Transactional
    public UserDB createUser(UserDB user) {
        validateInvestorOrBidder(user);
        validateIfUserExist(user);
        user.setId(UUID.randomUUID().toString());
        userMapper.saveUser(user);
        return user;
    }

    @Override
    public UserDB findUserById(String id) {
        return Optional.ofNullable(userMapper.findUserById(id))
                .orElseThrow(() -> new InternalWebException(HttpStatus.BAD_REQUEST,
                        ExceptionUtils.addError(ExceptionEnum.
                                OBJECT_DOES_NOT_EXIST, null, "user", id)));
    }

    private void validateInvestorOrBidder(UserDB userDB) {
        Optional.ofNullable(userDB.getInvestorId())
                .ifPresent(invId -> {
                        Optional.ofNullable(investorMapper.findInvestorById(invId))
                                .orElseThrow(() -> new InternalWebException(HttpStatus.BAD_REQUEST,
                                        ExceptionUtils.addError(ExceptionEnum.
                                                OBJECT_DOES_NOT_EXIST, null, "investor", invId)));

                });

        Optional.ofNullable(userDB.getBidderId())
                .ifPresent(biddId ->
                        Optional.ofNullable(bidderMapper.findBidderById(biddId))
                                .orElseThrow(() -> new InternalWebException(HttpStatus.BAD_REQUEST,
                                        ExceptionUtils.addError(ExceptionEnum.
                                                OBJECT_DOES_NOT_EXIST, null, "bidder", biddId))));
    }

    private void validateIfUserExist(UserDB userDB) {
        Optional.ofNullable(userMapper.findUserByUsername(userDB.getUsername()))
                .ifPresent(u -> {
                    throw new InternalWebException(HttpStatus.BAD_REQUEST,
                            ExceptionUtils.addError(ExceptionEnum.
                                    OBJECT_ALLREADY_EXIST, null, "user", "username", userDB.getUsername()));
                });
    }

}
