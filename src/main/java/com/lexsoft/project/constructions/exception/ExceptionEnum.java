package com.lexsoft.project.constructions.exception;

import lombok.Getter;

@Getter
public enum ExceptionEnum {

    PROPERTY_IS_MANDATORY(1002,"Property %s is mandatory."),
    OBJECT_CONTAIN_ONE_VALUE(1003,"Obejct %s can contain one value from list: %s"),
    OBJECT_DOES_NOT_EXIST(1000, "Object %s identified by %s does not exist."),
    OBJECT_ALLREADY_EXIST(1001, "Object %s identified by param %s with value %s allready exist."),

    TENDER_IS_ALLREADY_ACTIVE(2000, "Tender is allready active."),
    TENDER_IS_NO_LONGER_AVAILABLE(2001, "Tender identified with %s is no longer available."),
    TENDER_HAS_BETTER_OFFER(2002,"Tender has better offers : %s"),
    TENDER_DOES_NOT_BELONG_TO_INVESTOR(2003,"Tender does not belong to investor %s, so the user with id: %s that is not bound to investor cant accept the offer."),

    NOT_INVESTOR_USER(3000,"User identified with id: %s is not an investor user."),
    NOT_BIDDER_USER(3001,"User identified with id: %s is not an investor user.");

    private Integer code;
    private String message;

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
