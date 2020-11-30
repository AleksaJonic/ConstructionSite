package com.lexsoft.project.constructions.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OfferDto {

    private String id;
    private Boolean accept;
    private String description;
    private Double amount;
    private UserDto user;
    private UserDto acceptOfferUser;
    private TenderDto tender;
    private String status;
}
