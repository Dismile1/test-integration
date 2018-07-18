package com.ratiose.testtask.facade;

import com.ratiose.testtask.dto.UserData;

public interface UserFacade {

    void registerUser(UserData userData);

    void addFavouriteActor(String actorId);

    void removeFavouriteActor(String actorId);

    void markMovieWatched(String movieId);
}
