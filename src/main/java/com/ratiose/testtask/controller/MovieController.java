package com.ratiose.testtask.controller;

import com.ratiose.testtask.dto.DiscoverMoviesResponse;
import com.ratiose.testtask.facade.UserFacade;
import com.ratiose.testtask.service.tmdb.TmdbApi;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private UserFacade userFacade;
    @Autowired
    private TmdbApi tmdbApi;


    @PostMapping(value = "/watch")
    @ResponseStatus(HttpStatus.ACCEPTED)
    private void markWatched(@NotBlank @RequestParam String movieId) {
        userFacade.markMovieWatched(movieId);
    }

    @GetMapping(value = "/discover")
    public DiscoverMoviesResponse discoverMovies(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam int year,
                                                 @RequestParam int month) {
        return tmdbApi.discoverMovies(page, year, month);
    }
}
