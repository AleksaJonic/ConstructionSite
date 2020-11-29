package com.lexsoft.project.constructions.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexsoft.project.constructions.model.db.BidderDB;
import com.lexsoft.project.constructions.model.db.InvestorDB;
import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.model.dto.BidderDto;
import com.lexsoft.project.constructions.model.dto.InvestorDto;
import com.lexsoft.project.constructions.model.dto.UserDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class TestingData extends FileUtils {

    private static final String DATA_FOLDER = "/json/";

    @SneakyThrows
    public List<InvestorDB> getDBInvestors() {
        ObjectMapper mapper = new ObjectMapper();
        String fileAsString = getFileAsString(DATA_FOLDER.concat("investorsDb.json"));
        InvestorDB[] dbInvestors = mapper.readValue(fileAsString, InvestorDB[].class);
        return Arrays.asList(dbInvestors);
    }

    @SneakyThrows
    public List<BidderDB> getDBBidders()  {
        ObjectMapper mapper = new ObjectMapper();
        String fileAsString = getFileAsString(DATA_FOLDER.concat("biddersDb.json"));
        BidderDB[] dbBidders = mapper.readValue(fileAsString, BidderDB[].class);
        return Arrays.asList(dbBidders);
    }

    @SneakyThrows
    public List<UserDB> getDBUsers() {
        ObjectMapper mapper = new ObjectMapper();
        String fileAsString = getFileAsString(DATA_FOLDER.concat("usersDb.json"));
        UserDB[] dbUsers = mapper.readValue(fileAsString, UserDB[].class);
        return Arrays.asList(dbUsers);
    }

    @SneakyThrows
    public List<InvestorDto> getDtoInvestors() {
        ObjectMapper mapper = new ObjectMapper();
        String fileAsString = getFileAsString(DATA_FOLDER.concat("investorDto.json"));
        InvestorDto[] investors = mapper.readValue(fileAsString, InvestorDto[].class);
        return Arrays.asList(investors);
    }

    @SneakyThrows
    public List<BidderDto> getDtoBidders()  {
        ObjectMapper mapper = new ObjectMapper();
        String fileAsString = getFileAsString(DATA_FOLDER.concat("bidderDto.json"));
        BidderDto[] bidders = mapper.readValue(fileAsString, BidderDto[].class);
        return Arrays.asList(bidders);
    }

    @SneakyThrows
    public List<UserDto> getDtoUsers()  {
        ObjectMapper mapper = new ObjectMapper();
        String fileAsString = getFileAsString(DATA_FOLDER.concat("usersDto.json"));
        UserDto[] users = mapper.readValue(fileAsString, UserDto[].class);
        return Arrays.asList(users);
    }


}
