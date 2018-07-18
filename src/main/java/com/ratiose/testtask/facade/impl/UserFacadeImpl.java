package com.ratiose.testtask.facade.impl;

import com.ratiose.testtask.dto.UserData;
import com.ratiose.testtask.entity.User;
import com.ratiose.testtask.facade.UserFacade;
import com.ratiose.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private Converter<UserData, User> userDataConverter;
    @Autowired
    private UserService userService;

    @Override
    public void registerUser(UserData userData) {
        User user = userDataConverter.convert(userData);
        userService.registerUser(user);
    }

    @Override
    public void addFavouriteActor(String actorId) {
        userService.addFavouriteActor(actorId);
    }

    @Override
    public void removeFavouriteActor(String actorId) {
        userService.removeFavouriteActor(actorId);
    }

    @Override
    public void markMovieWatched(String movieId) {
        userService.markMovieWatched(movieId);
    }
}
