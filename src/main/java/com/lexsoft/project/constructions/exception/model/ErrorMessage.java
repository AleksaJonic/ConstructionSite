package com.lexsoft.project.constructions.exception.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {

    Integer code;
    String message;

}
