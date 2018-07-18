package com.ratiose.testtask.service.tmdb;

import com.ratiose.testtask.dto.DiscoverMoviesResponse;

public interface TmdbApi {

    DiscoverMoviesResponse discoverMovies(int page, int year, int month);
}
