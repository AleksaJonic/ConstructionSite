package com.lexsoft.project.constructions.service;


import com.lexsoft.project.constructions.model.db.InvestorDB;

import java.util.List;

public interface InvestorService {

    InvestorDB saveInvestor(InvestorDB investor);
    InvestorDB findInvestor(String id);
    List<InvestorDB> findAllInvestors();
    Boolean deleteInvestor(String id);

}
