package com.lexsoft.project.constructions.controller;

import com.lexsoft.project.constructions.model.db.OfferDB;
import com.lexsoft.project.constructions.model.db.TenderDB;
import com.lexsoft.project.constructions.model.dto.AcceptOfferDto;
import com.lexsoft.project.constructions.model.dto.OfferDto;
import com.lexsoft.project.constructions.model.dto.TenderDto;
import com.lexsoft.project.constructions.service.OfferService;
import com.lexsoft.project.constructions.service.TenderService;
import com.lexsoft.project.constructions.transformer.Transformer;
import com.lexsoft.project.constructions.validation.impl.TenderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    OfferService offerService;
    @Autowired
    Transformer<OfferDto, OfferDB> transformer;

    @PostMapping
    public ResponseEntity<OfferDto> placeOffer(@RequestBody OfferDto offerDto) {
        OfferDB transformed = transformer.transform(offerDto);
        OfferDB offerDB = offerService.placeOffer(transformed);
        OfferDto resultOfferDto = transformer.transformBackwards(offerDB);
        return ResponseEntity.ok(resultOfferDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OfferDto> getOffer(@PathVariable("id") String id) {
        OfferDB offerById = offerService.findOfferById(id);
        OfferDto offerDto = transformer.transformBackwards(offerById);
        return ResponseEntity.ok(offerDto);
    }

    @GetMapping
    public ResponseEntity<List<OfferDto>> findOffers(@RequestParam(value = "userId", required = false) String userId,
                                                     @RequestParam(value = "bidderId", required = false) String bidderId,
                                                     @RequestParam(value = "tenderId", required = false) String tenderId,
                                                     @RequestParam(value = "accepted",required = false) Boolean accepted
                                                     ) {

        List<OfferDB> offers = offerService.findOffers(userId, bidderId, tenderId, accepted);
        List<OfferDto> resultOfferList = transformer.transformBackwardsBatch(offers);
        return ResponseEntity.ok(resultOfferList);
    }


    @PutMapping("/{id}/accept")
    public ResponseEntity<OfferDto> findOffers(@PathVariable("id") String id,
                                                     @RequestBody AcceptOfferDto acceptOfferDto) {

        OfferDB offerDB = offerService.acceptOffer(id,acceptOfferDto);
        OfferDto offerDto = transformer.transformBackwards(offerDB);
        return ResponseEntity.ok(offerDto);
    }


}
