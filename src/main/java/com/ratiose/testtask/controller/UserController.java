package com.ratiose.testtask.controller;

import com.ratiose.testtask.dto.UserData;
import com.ratiose.testtask.exception.DuplicateEmailException;
import com.ratiose.testtask.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserFacade userFacade;

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid UserData userData) {
        userFacade.registerUser(userData);
    }

    @ExceptionHandler({DuplicateEmailException.class})
    public ResponseEntity handleDuplicateEmailException(DuplicateEmailException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
