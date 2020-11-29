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
public class BidderDB {

    private String id;
    private String name;
    private List<UserDB> users;

    private Boolean hasWorkingReference;



}
