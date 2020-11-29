package com.lexsoft.project.constructions.transformer.impl;

import com.lexsoft.project.constructions.model.db.InvestorDB;
import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.model.dto.InvestorDto;
import com.lexsoft.project.constructions.model.dto.UserDto;
import com.lexsoft.project.constructions.transformer.Transformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InvestorTransformer implements Transformer<InvestorDto, InvestorDB> {

    @Autowired
    Transformer<UserDto, UserDB> userTransformer;

    @Override
    public InvestorDB transform(InvestorDto investorDto) {
        return InvestorDB.builder()
                .id(investorDto.getId())
                .description(investorDto.getDescription())
                .name(investorDto.getName())
                .users(userTransformer.transformBatch(investorDto.getUsers()))
                .build();
    }

    @Override
    public List<InvestorDB> transformBatch(List<InvestorDto> list) {
        List<InvestorDB> investorDbList = new ArrayList<>();
        Optional.ofNullable(list).ifPresent(l -> l.forEach(e -> investorDbList.add(transform(e))));
        return investorDbList;
    }

    @Override
    public InvestorDto transformBackwards(InvestorDB investorDB) {
        return InvestorDto.builder()
                .id(investorDB.getId())
                .description(investorDB.getDescription())
                .name(investorDB.getName())
                .users(userTransformer.transformBackwardsBatch(investorDB.getUsers()))
                .build();
    }

    @Override
    public List<InvestorDto> transformBackwardsBatch(List<InvestorDB> list) {
        List<InvestorDto> investorDtoList = new ArrayList<>();
        Optional.ofNullable(list).ifPresent(l -> l.forEach(e -> investorDtoList.add(transformBackwards(e))));
        return investorDtoList;
    }
}
