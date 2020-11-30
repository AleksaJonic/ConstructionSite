package com.lexsoft.project.constructions.repository;

import com.lexsoft.project.constructions.model.db.TenderDB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TenderMapper {

    public void saveTender(@Param("tender") TenderDB tenderDB);

    public TenderDB findTenderById(@Param("id") String id);

    public List<TenderDB> findAllTenders(@Param("userId") String userId,
                                         @Param("investorId") String investorId,
                                         @Param("active") Boolean active);

    public void deleteTender(@Param("id") String id,
                             @Param("investorId") String investorId,
                             @Param("userId") String userId);

    public void deactivateTender(@Param("id") String id);

}
