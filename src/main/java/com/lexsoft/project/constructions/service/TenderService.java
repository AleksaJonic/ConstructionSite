package com.lexsoft.project.constructions.service;

import com.lexsoft.project.constructions.model.db.TenderDB;

import java.util.List;

public interface TenderService {

    public TenderDB saveTender(TenderDB tenderDB);
    public TenderDB findTenderById(String id);
    public List<TenderDB> findAllTenders(String userId, String investorId, Boolean active);
    public TenderDB deactivateTender(String id);
    public Boolean deleteTender(String id);
    public void deleteAllInactiveTenders(String investorId, String userId);


}
