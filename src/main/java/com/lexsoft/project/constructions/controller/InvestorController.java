package com.lexsoft.project.constructions.controller;

import com.lexsoft.project.constructions.model.db.InvestorDB;
import com.lexsoft.project.constructions.model.dto.InvestorDto;
import com.lexsoft.project.constructions.service.InvestorService;
import com.lexsoft.project.constructions.transformer.Transformer;

import com.lexsoft.project.constructions.validation.impl.InvestorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/investors")
public class InvestorController {


    @Autowired
    InvestorValidator investorValidator;

    @Autowired
    Transformer<InvestorDto, InvestorDB> transformer;

    @Autowired
    InvestorService investorService;

    @PostMapping
    public ResponseEntity<InvestorDto> createInvestor(@RequestBody InvestorDto investorDto) {
        investorValidator.validate(investorDto, null);
        InvestorDB transformedInvestor = transformer.transform(investorDto);
        InvestorDB investorDB = investorService.saveInvestor(transformedInvestor);
        InvestorDto resultInvestorDto = transformer.transformBackwards(investorDB);
        return ResponseEntity.ok(resultInvestorDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<InvestorDto> getInvestor(@PathVariable("id") String id) {
        InvestorDB investorDB = investorService.findInvestor(id);
        InvestorDto resultInvestorDto = transformer.transformBackwards(investorDB);
        return ResponseEntity.ok(resultInvestorDto);
    }

    @GetMapping
    public ResponseEntity<List<InvestorDto>> findAllInvestors() {
        List<InvestorDB> investorsDB = investorService.findAllInvestors();
        List<InvestorDto> resultInvestorsList = transformer.transformBackwardsBatch(investorsDB);
        return ResponseEntity.ok(resultInvestorsList);
    }

}
