package com.lexsoft.project.constructions.transformer.impl;

import com.lexsoft.project.constructions.model.db.InvestorDB;
import com.lexsoft.project.constructions.model.db.TenderDB;
import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.model.dto.InvestorDto;
import com.lexsoft.project.constructions.model.dto.TenderDto;
import com.lexsoft.project.constructions.model.dto.UserDto;
import com.lexsoft.project.constructions.transformer.Transformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TenderTransformer implements Transformer<TenderDto, TenderDB> {

    @Autowired
    Transformer<UserDto, UserDB> userTransformer;
    @Autowired
    Transformer<InvestorDto, InvestorDB> investorTransformer;


    @Override
    public TenderDB transform(TenderDto tenderDto) {
        return TenderDB.builder()
                .id(tenderDto.getId())
                .name(tenderDto.getName())
                .description(tenderDto.getDescription())
                .investor(investorTransformer.transform(tenderDto.getInvestor()))
                .user(userTransformer.transform(tenderDto.getUser()))
                .build();
    }

    @Override
    public List<TenderDB> transformBatch(List<TenderDto> list) {
        List<TenderDB> tendersList = new ArrayList<>();
        Optional.ofNullable(list).ifPresent(l -> l.forEach(e -> tendersList.add(transform(e))));
        return tendersList;
    }

    @Override
    public TenderDto transformBackwards(TenderDB tenderDB) {
        return TenderDto.builder()
                .id(tenderDB.getId())
                .active(tenderDB.getActive())
                .name(tenderDB.getName())
                .description(tenderDB.getDescription())
                .investor(investorTransformer.transformBackwards(tenderDB.getInvestor()))
                .user(userTransformer.transformBackwards(tenderDB.getUser()))
                .build();
    }

    @Override
    public List<TenderDto> transformBackwardsBatch(List<TenderDB> list) {
        List<TenderDto> tendersList = new ArrayList<>();
        Optional.ofNullable(list).ifPresent(l -> l.forEach(e -> tendersList.add(transformBackwards(e))));
        return tendersList;
    }
}
