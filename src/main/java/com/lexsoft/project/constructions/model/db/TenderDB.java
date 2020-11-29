package com.lexsoft.project.constructions.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenderDB {

    private String id;
    private String name;
    private String description;
    private Boolean active;
    private UserDB user;
    private InvestorDB investor;

}

