package com.ratiose.testtask.controller;

import com.ratiose.testtask.facade.UserFacade;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/actor")
public class ActorController {

    @Autowired
    private UserFacade userFacade;


    @PostMapping(value = "/favourites")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addFavouriteActor(@NotBlank @RequestParam String actorId) {
        userFacade.addFavouriteActor(actorId);
    }


    @DeleteMapping(value = "/favourites")
    @ResponseStatus(HttpStatus.OK)
    public void removeFavouriteActor(@NotBlank @RequestParam String actorId) {
        userFacade.removeFavouriteActor(actorId);
    }
}
