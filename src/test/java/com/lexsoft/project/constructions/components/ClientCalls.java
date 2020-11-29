package com.lexsoft.project.constructions.components;

import com.lexsoft.project.constructions.model.dto.BidderDto;
import com.lexsoft.project.constructions.model.dto.InvestorDto;
import com.lexsoft.project.constructions.model.dto.UserDto;

import lombok.NoArgsConstructor;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
@NoArgsConstructor
public class ClientCalls extends ClientCallExecution {

    public ResponseEntity<InvestorDto[]> findInvestors(Integer port, TestRestTemplate template) {
        return executeCall("/investors", port, HttpMethod.GET, null, template, InvestorDto[].class);
    }

    public ResponseEntity<InvestorDto> findOneInvestor(Integer port, TestRestTemplate template, String id) {
        return executeCall("/investors/".concat(id), port, HttpMethod.GET,null, template, InvestorDto.class);
    }

    public synchronized  ResponseEntity<InvestorDto> createInvestor(Integer port, TestRestTemplate template, InvestorDto investorDto) {
        return executeCall("/investors", port, HttpMethod.POST,investorDto, template, InvestorDto.class);
    }


    public ResponseEntity<InvestorDto> findOneBidder(Integer port, TestRestTemplate template, String id) {
        return executeCall("/bidders/".concat(id), port, HttpMethod.GET,null, template, InvestorDto.class);
    }


    public ResponseEntity<BidderDto> createBidder(Integer port, TestRestTemplate template, BidderDto body) {
        return executeCall("/bidders", port, HttpMethod.POST,body, template, BidderDto.class);
    }

    public ResponseEntity<BidderDto[]> findBidders(Integer port, TestRestTemplate template) {
        return executeCall("/bidders", port, HttpMethod.GET,null, template, BidderDto[].class);
    }

    public ResponseEntity<UserDto> findOneUser(Integer port, TestRestTemplate template, String id) {
        return executeCall("/users/".concat(id), port, HttpMethod.GET,null, template, UserDto.class);
    }

    public ResponseEntity<UserDto> createUser(Integer port, TestRestTemplate template,UserDto userDto) {
        return executeCall("/users", port, HttpMethod.POST,userDto, template, UserDto.class);
    }


}
