package com.ratiose.testtask.service;

import com.ratiose.testtask.entity.User;

public interface UserService {

    void registerUser(User user);

    void addFavouriteActor(String actorId);

    void removeFavouriteActor(String actorId);

    void markMovieWatched(String movieId);

    User getCurrentUser();
}

