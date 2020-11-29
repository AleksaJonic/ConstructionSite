package com.lexsoft.project.constructions.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDB {

    private String id;
    private String username;
    private String password;
    private String investorId;
    private String bidderId;

}
