package com.lexsoft.project.constructions.transformer.impl;

import com.lexsoft.project.constructions.model.db.BidderDB;
import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.model.dto.BidderDto;
import com.lexsoft.project.constructions.model.dto.UserDto;
import com.lexsoft.project.constructions.transformer.Transformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BidderTransformer implements Transformer<BidderDto, BidderDB> {

    @Autowired
    Transformer<UserDto, UserDB> userTransformer;

    @Override
    public BidderDB transform(BidderDto bidderDto) {
        return BidderDB.builder()
                .id(bidderDto.getId())
                .hasWorkingReference(bidderDto.getWorkingReference())
                .name(bidderDto.getName())
                .users(userTransformer.transformBatch(bidderDto.getUsers()))
                .build();
    }

    @Override
    public List<BidderDB> transformBatch(List<BidderDto> list) {
        List<BidderDB> bidderDbList = new ArrayList<>();
        Optional.ofNullable(list).ifPresent(l -> l.forEach(e -> bidderDbList.add(transform(e))));
        return bidderDbList;
    }

    @Override
    public BidderDto transformBackwards(BidderDB bidderDB) {
        return BidderDto.builder()
                .id(bidderDB.getId())
                .name(bidderDB.getName())
                .workingReference(bidderDB.getHasWorkingReference())
                .users(userTransformer.transformBackwardsBatch(bidderDB.getUsers()))
                .build();
    }

    @Override
    public List<BidderDto> transformBackwardsBatch(List<BidderDB> list) {
        List<BidderDto> bidderDtoList = new ArrayList<>();
        Optional.ofNullable(list).ifPresent(l -> l.forEach(e -> bidderDtoList.add(transformBackwards(e))));
        return bidderDtoList;
    }
}
