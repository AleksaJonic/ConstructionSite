package com.lexsoft.project.constructions.controller;

import com.lexsoft.project.constructions.model.db.BidderDB;
import com.lexsoft.project.constructions.model.db.InvestorDB;
import com.lexsoft.project.constructions.model.dto.BidderDto;
import com.lexsoft.project.constructions.model.dto.InvestorDto;
import com.lexsoft.project.constructions.service.BidderService;
import com.lexsoft.project.constructions.service.InvestorService;
import com.lexsoft.project.constructions.transformer.Transformer;
import com.lexsoft.project.constructions.validation.impl.BidderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bidders")
public class BidderController {

    @Autowired
    BidderValidator bidderValidator;

    @Autowired
    Transformer<BidderDto, BidderDB> transformer;

    @Autowired
    BidderService bidderService;

    @PostMapping
    public ResponseEntity<BidderDto> createBidder(@RequestBody BidderDto bidderDto){
        bidderValidator.validate(bidderDto,null);
        BidderDB transformedBidder = transformer.transform(bidderDto);
        BidderDB bidderDb = bidderService.saveBidder(transformedBidder);
        BidderDto resultBidderDto = transformer.transformBackwards(bidderDb);
        return ResponseEntity.ok(resultBidderDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BidderDto> getBidder(@PathVariable("id") String id){
        BidderDB bidderDB = bidderService.findBidderById(id);
        BidderDto resultBidderDto = transformer.transformBackwards(bidderDB);
        return ResponseEntity.ok(resultBidderDto);
    }

    @GetMapping
    public ResponseEntity<List<BidderDto>> findAllBidders() {
        List<BidderDB> bidders = bidderService.findAllBidders();
        List<BidderDto> resultBiddersList = transformer.transformBackwardsBatch(bidders);
        return ResponseEntity.ok(resultBiddersList);
    }

}
