package com.lexsoft.project.constructions.controller;

import com.lexsoft.project.constructions.model.db.UserDB;
import com.lexsoft.project.constructions.model.dto.UserDto;
import com.lexsoft.project.constructions.service.UserService;
import com.lexsoft.project.constructions.transformer.Transformer;

import com.lexsoft.project.constructions.validation.impl.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {


    @Autowired
    UserValidator userValidator;
    @Autowired
    UserService userService;
    @Autowired
    Transformer<UserDto, UserDB> transformer;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") String id){
        UserDB userById = userService.findUserById(id);
        UserDto userDto = transformer.transformBackwards(userById);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        userValidator.validate(userDto, null);
        UserDB transformedUser = transformer.transform(userDto);
        UserDB userDb = userService.createUser(transformedUser);
        UserDto resultUser = transformer.transformBackwards(userDb);
        return ResponseEntity.ok(resultUser);
    }




}
