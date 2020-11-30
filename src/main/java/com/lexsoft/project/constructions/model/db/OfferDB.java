package com.lexsoft.project.constructions.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferDB {

    private String id;
    private Double amount;
    private String description;
    private Boolean accepted;
    private TenderDB tender;
    private BidderDB bidder;
    private String status;
    private UserDB user;
    private UserDB userThatAccepted;
}
