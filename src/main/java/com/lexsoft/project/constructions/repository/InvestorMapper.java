package com.lexsoft.project.constructions.repository;

import com.lexsoft.project.constructions.model.db.InvestorDB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InvestorMapper {

    public void saveInvestor(@Param("investor") InvestorDB investorDB);
    public InvestorDB findInvestorById(@Param("id") String id);
    public List<InvestorDB> findAllInvestors();
    public void deleteInvestorById(@Param("id") String id);

}
