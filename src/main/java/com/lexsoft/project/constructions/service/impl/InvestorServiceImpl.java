package com.lexsoft.project.constructions.service.impl;

import com.lexsoft.project.constructions.exception.ExceptionEnum;
import com.lexsoft.project.constructions.exception.ExceptionUtils;
import com.lexsoft.project.constructions.exception.types.InternalWebException;
import com.lexsoft.project.constructions.model.db.InvestorDB;
import com.lexsoft.project.constructions.repository.InvestorMapper;
import com.lexsoft.project.constructions.repository.UserMapper;
import com.lexsoft.project.constructions.service.InvestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvestorServiceImpl implements InvestorService {

    @Autowired
    InvestorMapper investorMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public InvestorDB saveInvestor(InvestorDB investor) {
        String investorId = UUID.randomUUID().toString();
        investor.setId(investorId);
        investorMapper.saveInvestor(investor);

        Optional.ofNullable(investor.getUsers())
                .ifPresent(users -> {
                    users.forEach(u -> {
                        u.setInvestorId(investorId);
                        u.setId(UUID.randomUUID().toString());
                    });
                    userMapper.saveBatchUsers(users);
                });

        return investor;
    }

    @Override
    public InvestorDB findInvestor(String id) {
        return Optional.ofNullable(investorMapper.findInvestorById(id))
                .orElseThrow(() -> new InternalWebException(HttpStatus.NOT_FOUND,
                        ExceptionUtils.addError(ExceptionEnum.
                                OBJECT_DOES_NOT_EXIST, null, "investor", id)));
    }

    @Override
    public List<InvestorDB> findAllInvestors() {
        return investorMapper.findAllInvestors();
    }

    @Override
    @Transactional
    public Boolean deleteInvestor(String id) {
        userMapper.deleteInvestorUsers(id);
        investorMapper.deleteInvestorById(id);
        return Boolean.TRUE;
    }


}
