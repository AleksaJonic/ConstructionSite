package com.lexsoft.project.constructions.components;

import com.lexsoft.project.constructions.model.db.BidderDB;
import com.lexsoft.project.constructions.model.dto.*;

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


    public ResponseEntity<BidderDto> findOneBidder(Integer port, TestRestTemplate template, String id) {
        return executeCall("/bidders/".concat(id), port, HttpMethod.GET,null, template, BidderDto.class);
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

    public ResponseEntity<TenderDto> findOneTender(Integer port, TestRestTemplate template, String id) {
        return executeCall("/tenders/".concat(id), port, HttpMethod.GET,null, template, TenderDto.class);
    }

    public ResponseEntity<TenderDto> createTender(Integer port, TestRestTemplate template, TenderDto tenderDto) {
        return executeCall("/tenders", port, HttpMethod.POST,tenderDto, template, TenderDto.class);
    }

    public ResponseEntity<TenderDto[]> getMultipleTenders(Integer port, TestRestTemplate template){
        return executeCall("/tenders", port, HttpMethod.GET,null, template, TenderDto[].class);
    }

    public ResponseEntity<OfferDto[]> getOffersForTender(Integer port, TestRestTemplate template, String tenderId){
        return executeCall("/offers?tenderId=".concat(tenderId),port,HttpMethod.GET,null,template,OfferDto[].class);
    }

    public ResponseEntity<OfferDto[]> getOffersForBidder(Integer port, TestRestTemplate template, String bidderId){
        return executeCall("/offers?bidderId=".concat(bidderId),port,HttpMethod.GET,null,template,OfferDto[].class);
    }

    public ResponseEntity<OfferDto> getSingleOfferById(Integer port, TestRestTemplate template, String offerId){
        return executeCall("/offers/".concat(offerId),port,HttpMethod.GET,null,template,OfferDto.class);
    }

    public ResponseEntity<OfferDto> placeOffer(Integer port, TestRestTemplate template, OfferDto offerDto){
        return executeCall("/offers",port,HttpMethod.POST,offerDto,template,OfferDto.class);
    }

    public ResponseEntity<String> acceptOffer(Integer port, TestRestTemplate template, String offerId, AcceptOfferDto acceptOfferDto){
        return executeCall("/offers/".concat(offerId).concat("/accept"),port,HttpMethod.PUT,acceptOfferDto,template,String.class);
    }

}
