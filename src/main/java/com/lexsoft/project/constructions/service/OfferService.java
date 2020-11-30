package com.lexsoft.project.constructions.service;

import com.lexsoft.project.constructions.model.db.OfferDB;
import com.lexsoft.project.constructions.model.dto.AcceptOfferDto;

import java.util.List;

public interface OfferService {

    OfferDB findOfferById(String id);
    List<OfferDB> findOffers(String userId, String bidderId, String tenderId, Boolean accepted);
    OfferDB placeOffer(OfferDB offer);
    OfferDB acceptOffer(String id,AcceptOfferDto acceptOfferDto);



}
