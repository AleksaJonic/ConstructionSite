package com.lexsoft.project.constructions.service;

import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.model.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDB createUser(UserDB user);
    UserDB findUserById(String id);

}
