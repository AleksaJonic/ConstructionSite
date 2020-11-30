package com.lexsoft.project.constructions.repository;

import com.lexsoft.project.constructions.model.db.OfferDB;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OfferMapper {

    OfferDB findOfferById(@Param("id")String id);
    List<OfferDB> findOffers(@Param("userId") String userId,
                             @Param("bidderId") String bidderId,
                             @Param("tenderId") String tenderId,
                             @Param("accepted") Boolean accepted);

    void placeOffer(@Param("offer") OfferDB offer);

    void acceptOffer(@Param("id") String id,
                        @Param("acceptUserId") String acceptUserId,
                        @Param("status") String status);

    void rejectTenderOffers(@Param("tenderId") String tenderId, @Param("status") String status);

}
