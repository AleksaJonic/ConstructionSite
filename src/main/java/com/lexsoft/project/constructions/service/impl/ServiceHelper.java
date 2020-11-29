package com.lexsoft.project.constructions.service.impl;

import com.lexsoft.project.constructions.exception.ExceptionEnum;
import com.lexsoft.project.constructions.exception.ExceptionUtils;
import com.lexsoft.project.constructions.exception.types.InternalWebException;
import com.lexsoft.project.constructions.model.db.BidderDB;
import com.lexsoft.project.constructions.model.db.InvestorDB;
import com.lexsoft.project.constructions.model.db.TenderDB;
import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.repository.BidderMapper;
import com.lexsoft.project.constructions.repository.InvestorMapper;
import com.lexsoft.project.constructions.repository.TenderMapper;
import com.lexsoft.project.constructions.repository.UserMapper;

import org.springframework.http.HttpStatus;

import java.util.Optional;

public  abstract class ServiceHelper {

    protected UserDB validateIfUserExist(String userId, UserMapper userMapper){
        return Optional.ofNullable(userMapper.findUserById(userId))
                .orElseThrow(() -> new InternalWebException(HttpStatus.BAD_REQUEST,
                        ExceptionUtils.addError(ExceptionEnum.
                                OBJECT_DOES_NOT_EXIST, null, "user", userId)));
    }

    protected InvestorDB validateIfInvestorExist(String investorId, InvestorMapper investorMapper){
        return Optional.ofNullable(investorMapper.findInvestorById(investorId))
                .orElseThrow(() -> new InternalWebException(HttpStatus.BAD_REQUEST,
                        ExceptionUtils.addError(ExceptionEnum.
                                OBJECT_DOES_NOT_EXIST, null, "investor", investorId)));
    }

    protected BidderDB validateIfBidderExist(String bidderId, BidderMapper bidderMapper){
        return Optional.ofNullable(bidderMapper.findBidderById(bidderId))
                .orElseThrow(() -> new InternalWebException(HttpStatus.BAD_REQUEST,
                        ExceptionUtils.addError(ExceptionEnum.
                                OBJECT_DOES_NOT_EXIST, null, "bidder", bidderId)));
    }

    protected TenderDB validateIfTenderExist(String tenderId, TenderMapper tenderMapper){
        return Optional.ofNullable(tenderMapper.findTenderById(tenderId))
                .orElseThrow(() -> new InternalWebException(HttpStatus.BAD_REQUEST,
                        ExceptionUtils.addError(ExceptionEnum.
                                OBJECT_DOES_NOT_EXIST, null, "tender", tenderId)));
    }




}
