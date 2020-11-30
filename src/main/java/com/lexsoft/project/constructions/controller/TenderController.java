package com.lexsoft.project.constructions.controller;

import com.lexsoft.project.constructions.model.db.TenderDB;
import com.lexsoft.project.constructions.model.dto.TenderDto;
import com.lexsoft.project.constructions.service.TenderService;
import com.lexsoft.project.constructions.transformer.Transformer;
import com.lexsoft.project.constructions.validation.impl.TenderValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tenders")
public class TenderController {


    @Autowired
    TenderValidator tenderValidator;

    @Autowired
    Transformer<TenderDto, TenderDB> transformer;

    @Autowired
    TenderService tenderService;

    @PostMapping
    public ResponseEntity<TenderDto> createInvestor(@RequestBody TenderDto tenderDto) {
        tenderValidator.validate(tenderDto, null);
        TenderDB transformedInvestor = transformer.transform(tenderDto);
        TenderDB tenderDB = tenderService.saveTender(transformedInvestor);
        TenderDto resultTenderDto = transformer.transformBackwards(tenderDB);
        return ResponseEntity.ok(resultTenderDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TenderDto> getTender(@PathVariable("id") String id) {
        TenderDB tenderDb = tenderService.findTenderById(id);
        TenderDto resultTenderDto = transformer.transformBackwards(tenderDb);
        return ResponseEntity.ok(resultTenderDto);
    }

    @GetMapping
    public ResponseEntity<List<TenderDto>> findTenders(@RequestParam(value = "userId", required = false) String userId,
                                                       @RequestParam(value = "investorId", required = false) String investorId,
                                                       @RequestParam(value = "active", required = false) Boolean active) {


        List<TenderDB> tendersDb = tenderService.findAllTenders(userId, investorId, active);
        List<TenderDto> resultTenderList = transformer.transformBackwardsBatch(tendersDb);
        return ResponseEntity.ok(resultTenderList);
    }


}
