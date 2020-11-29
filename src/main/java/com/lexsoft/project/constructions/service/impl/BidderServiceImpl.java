package com.lexsoft.project.constructions.service.impl;

import com.lexsoft.project.constructions.exception.ExceptionEnum;
import com.lexsoft.project.constructions.exception.ExceptionUtils;
import com.lexsoft.project.constructions.exception.types.InternalWebException;
import com.lexsoft.project.constructions.model.db.BidderDB;
import com.lexsoft.project.constructions.repository.BidderMapper;
import com.lexsoft.project.constructions.repository.UserMapper;
import com.lexsoft.project.constructions.service.BidderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BidderServiceImpl implements BidderService {

    @Autowired
    BidderMapper bidderMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public BidderDB saveBidder(BidderDB bidder) {
        String bidderId = UUID.randomUUID().toString();
        bidder.setId(bidderId);
        bidderMapper.saveBidder(bidder);

        Optional.ofNullable(bidder.getUsers())
                .ifPresent(users -> {
                    users.forEach(u -> {
                        u.setBidderId(bidderId);
                        u.setId(UUID.randomUUID().toString());
                    });
                    System.out.println(users);

                    userMapper.saveBatchUsers(users);
                });

        return bidder;
    }

    @Override
    public BidderDB findBidderById(String id) {
        return Optional.ofNullable(bidderMapper.findBidderById(id))
                .orElseThrow(() -> new InternalWebException(HttpStatus.NOT_FOUND,
                        ExceptionUtils.addError(ExceptionEnum.
                                OBJECT_DOES_NOT_EXIST, null, "bidder", id)));
    }

    @Override
    public List<BidderDB> findAllBidders() {
        return bidderMapper.findAllBidders();
    }

    @Override
    @Transactional
    public Boolean deleteBidders(String id) {
        userMapper.deleteBidderUsers(id);
        bidderMapper.deleteBidderById(id);
        return Boolean.TRUE;
    }
}
