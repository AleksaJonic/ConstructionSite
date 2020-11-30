package com.lexsoft.project.constructions.transformer.impl;

import com.lexsoft.project.constructions.model.db.OfferDB;
import com.lexsoft.project.constructions.model.db.TenderDB;
import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.model.dto.OfferDto;
import com.lexsoft.project.constructions.model.dto.TenderDto;
import com.lexsoft.project.constructions.transformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OfferTransformer implements Transformer<OfferDto, OfferDB> {

    @Autowired
    UserTransformer userTransformer;
    @Autowired
    TenderTransformer tenderTransformer;


    @Override
    public OfferDB transform(OfferDto offerDto) {
        return OfferDB.builder()
                .accepted(offerDto.getAccept())
                .amount(offerDto.getAmount())
                .id(offerDto.getId())
                .tender(TenderDB.builder()
                        .id(offerDto.getTender().getId())
                        .build())
                .user(UserDB.builder()
                        .id(offerDto.getUser().getId())
                        .build())
                .description(offerDto.getDescription())
                .build();
    }

    @Override
    public List<OfferDB> transformBatch(List<OfferDto> list) {
        List<OfferDB> offerList = new ArrayList<>();
        Optional.ofNullable(list).ifPresent(l -> l.forEach(e -> offerList.add(transform(e))));
        return offerList;
    }

    @Override
    public OfferDto transformBackwards(OfferDB offerDB) {
        return OfferDto.builder()
                .accept(offerDB.getAccepted())
                .amount(offerDB.getAmount())
                .description(offerDB.getDescription())
                .user(userTransformer.transformBackwards(offerDB.getUser()))
                .status(offerDB.getStatus())
                .tender(TenderDto.builder()
                        .id(offerDB.getTender().getId())
                        .name(offerDB.getTender().getName())
                        .build())
                .id(offerDB.getId())
                .build();
    }

    @Override
    public List<OfferDto> transformBackwardsBatch(List<OfferDB> list) {
        List<OfferDto> offerList = new ArrayList<>();
        Optional.ofNullable(list).ifPresent(l -> l.forEach(e -> offerList.add(transformBackwards(e))));
        return offerList;
    }
}
