package com.lexsoft.project.constructions.service.impl;

import com.lexsoft.project.constructions.exception.ExceptionEnum;
import com.lexsoft.project.constructions.exception.ExceptionUtils;
import com.lexsoft.project.constructions.exception.types.InternalWebException;
import com.lexsoft.project.constructions.model.db.TenderDB;
import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.repository.InvestorMapper;
import com.lexsoft.project.constructions.repository.TenderMapper;
import com.lexsoft.project.constructions.repository.UserMapper;
import com.lexsoft.project.constructions.service.TenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TenderServiceImpl extends ServiceHelper implements TenderService {

    @Autowired
    TenderMapper tenderMapper;
    @Autowired
    InvestorMapper investorMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public TenderDB saveTender(TenderDB tenderDB) {

            UserDB user = validateIfIsInvestorUser(tenderDB.getUser().getId(),userMapper);
            tenderDB.setUser(user);
            tenderDB.setInvestor(validateIfInvestorExist(user.getInvestorId(),investorMapper));
            tenderDB.setId(UUID.randomUUID().toString());
            tenderDB.setActive(Boolean.TRUE);
            tenderMapper.saveTender(tenderDB);
            return tenderDB;
    }

    @Override
    public TenderDB findTenderById(String id) {
        return Optional.ofNullable(tenderMapper.findTenderById(id))
                .orElseThrow(() -> new InternalWebException(HttpStatus.NOT_FOUND,
                        ExceptionUtils.addError(ExceptionEnum.
                                OBJECT_DOES_NOT_EXIST, null, "tender", id)));    }

    @Override
    public List<TenderDB> findAllTenders(String userId, String investorId,Boolean active) {
        return tenderMapper.findAllTenders(userId,investorId,active);
    }

    @Override
    @Transactional
    public Boolean deleteTender(String id) {
        findTenderById(id);
        tenderMapper.deleteTender(id,null,null);
        return Boolean.TRUE;
    }



}
