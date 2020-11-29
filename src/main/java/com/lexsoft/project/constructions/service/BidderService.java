package com.lexsoft.project.constructions.service;

import com.lexsoft.project.constructions.model.db.BidderDB;

import java.util.List;

public interface BidderService {

    public BidderDB saveBidder(BidderDB bidderDB);
    public BidderDB findBidderById(String id);
    public List<BidderDB> findAllBidders();
    public Boolean deleteBidders(String id);

}
