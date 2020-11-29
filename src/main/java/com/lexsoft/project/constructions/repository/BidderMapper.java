package com.lexsoft.project.constructions.repository;

import com.lexsoft.project.constructions.model.db.BidderDB;
import com.lexsoft.project.constructions.model.db.UserDB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BidderMapper {

    public void saveBidder(@Param("bidder") BidderDB userDB);
    public BidderDB findBidderById(@Param("id") String id);
    public List<BidderDB> findAllBidders();
    public void deleteBidderById(@Param("id") String id);

}
