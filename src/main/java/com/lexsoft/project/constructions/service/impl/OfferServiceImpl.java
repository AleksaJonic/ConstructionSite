package com.lexsoft.project.constructions.service.impl;

import com.lexsoft.project.constructions.exception.ExceptionEnum;
import com.lexsoft.project.constructions.exception.ExceptionUtils;
import com.lexsoft.project.constructions.exception.types.InternalWebException;
import com.lexsoft.project.constructions.model.OfferEnum;
import com.lexsoft.project.constructions.model.db.BidderDB;
import com.lexsoft.project.constructions.model.db.OfferDB;
import com.lexsoft.project.constructions.model.db.TenderDB;
import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.model.dto.AcceptOfferDto;
import com.lexsoft.project.constructions.repository.BidderMapper;
import com.lexsoft.project.constructions.repository.OfferMapper;
import com.lexsoft.project.constructions.repository.TenderMapper;
import com.lexsoft.project.constructions.repository.UserMapper;
import com.lexsoft.project.constructions.service.OfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl extends ServiceHelper implements OfferService {

    @Autowired
    OfferMapper offerMapper;
    @Autowired
    BidderMapper bidderMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    TenderMapper tenderMapper;


    @Override
    public OfferDB findOfferById(String id) {
        return Optional.ofNullable(offerMapper.findOfferById(id))
                .orElseThrow(() -> new InternalWebException(HttpStatus.NOT_FOUND,
                        ExceptionUtils.addError(ExceptionEnum.
                                OBJECT_DOES_NOT_EXIST, null, "offer", id)));
    }

    @Override
    public List<OfferDB> findOffers(String userId, String bidderId, String tenderId, Boolean accepted) {
       Optional.ofNullable(bidderId).ifPresent(bi -> validateIfBidderExist(bi,bidderMapper));
       Optional.ofNullable(userId).ifPresent(ui -> validateIfUserExist(ui,userMapper));
       Optional.ofNullable(tenderId).ifPresent(ti -> validateIfTenderExist(ti,tenderMapper));
       return offerMapper.findOffers(userId,bidderId,tenderId,accepted);
    }

    @Override
    @Transactional
    public OfferDB placeOffer(OfferDB offer) {
        TenderDB tenderDB = validateIfTenderExist(offer.getTender().getId(), tenderMapper);
        if(Boolean.FALSE.equals(tenderDB.getActive())) {
            throw new InternalWebException(HttpStatus.BAD_REQUEST,
                    ExceptionUtils.addError(ExceptionEnum.
                            TENDER_IS_NO_LONGER_AVAILABLE, null, tenderDB.getId()));
        }
        UserDB userDB = validateIfUserExist(offer.getUser().getId(), userMapper);
        BidderDB bidderDB = validateIfBidderExist(userDB.getBidderId(), bidderMapper);

        offer.setBidder(bidderDB);
        offer.setId(UUID.randomUUID().toString());
        offer.setStatus(OfferEnum.PENDING.name());
        offer.setAccepted(Boolean.FALSE);
        offerMapper.placeOffer(offer);
        return offer;
    }

    @Override
    @Transactional
    public OfferDB acceptOffer(String id, AcceptOfferDto acceptOfferDto) {
        UserDB userDB = validateIfUserExist(acceptOfferDto.getAcceptUserId(), userMapper);
        OfferDB offerById = findOfferById(id);
        TenderDB tenderDB =  tenderMapper.findTenderById(offerById.getTender().getId());

        //check if user is investor or bidder and is inestor owner of requested tender
        if(userDB.getInvestorId() == null || !userDB.getInvestorId().equals(tenderDB.getInvestor().getId())){
            throw new InternalWebException(HttpStatus.BAD_REQUEST,
                    ExceptionUtils.addError(ExceptionEnum.TENDER_DOES_NOT_BELONG_TO_INVESTOR
                            , null, tenderDB.getInvestor().getId(),userDB.getId()));
        }

        //if force accept is not turned on, it will execute check is this most valuable offer
        if(acceptOfferDto.getForceAccept() == null || Boolean.FALSE.equals(acceptOfferDto.getForceAccept())){
            checkAllOffersForTender(tenderDB,offerById);
        }

        tenderMapper.deactivateTender(tenderDB.getId());
        offerMapper.rejectTenderOffers(tenderDB.getId(),OfferEnum.REJECTED.name());
        offerMapper.acceptOffer(offerById.getId(), userDB.getId(), OfferEnum.ACCEPTED.name());

        offerById.setAccepted(Boolean.TRUE);
        offerById.setStatus(OfferEnum.ACCEPTED.name());
        return offerById;
    }

    private void checkAllOffersForTender(TenderDB tenderDB, OfferDB offerDB) {
        List<OfferDB> betterOffers = new ArrayList<>();
        tenderDB.getOffers().forEach(offer -> {
            if (offer.getAmount() < offerDB.getAmount()) {
                betterOffers.add(offer);
            }
        });

        if (!betterOffers.isEmpty()) {
            String offerIds = betterOffers.stream()
                    .map(bo -> bo.getId())
                    .collect(Collectors.joining(","));
            throw new InternalWebException(HttpStatus.BAD_REQUEST,
                    ExceptionUtils.addError(ExceptionEnum.TENDER_HAS_BETTER_OFFER
                            , null, offerIds));
        }
    }
}
