package com.lexsoft.project.constructions.transformer.impl;

import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.model.dto.UserDto;
import com.lexsoft.project.constructions.transformer.Transformer;

import com.lexsoft.project.constructions.utils.HashingUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserTransformer implements Transformer<UserDto, UserDB> {

    @Override
    public UserDB transform(UserDto userDto) {
        return  UserDB.builder()
                    .id(userDto.getId())
                    .username(userDto.getUsername())
                    .password(Optional.ofNullable(userDto.getPassword())
                            .map(pass -> HashingUtils.hashPassword(pass))
                            .orElse(null))
                    .investorId(userDto.getInvestorId())
                    .bidderId(userDto.getBidderId())
                    .build();
    }

    @Override
    public List<UserDB> transformBatch(List<UserDto> list) {
        List<UserDB> usersDb = new ArrayList<>();
        Optional.ofNullable(list).ifPresent(l -> l.forEach(e -> usersDb.add(transform(e))));
        return usersDb;
    }

    @Override
    public UserDto transformBackwards(UserDB userDB) {
        return UserDto.builder()
                .id(userDB.getId())
                .username(userDB.getUsername())
                .bidderId(userDB.getBidderId())
                .investorId(userDB.getInvestorId())
                .build();
    }

    @Override
    public List<UserDto> transformBackwardsBatch(List<UserDB> list) {
        List<UserDto> userDtoList = new ArrayList<>();
        Optional.ofNullable(list).ifPresent(l -> l.forEach(e -> userDtoList.add(transformBackwards(e))));
        return userDtoList;
    }


}
