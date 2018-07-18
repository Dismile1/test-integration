package com.ratiose.testtask.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String email;
    @ElementCollection
    private Set<String> favouriteActors;
    @ElementCollection
    private Set<String> watchedMovies;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getFavouriteActors() {
        return favouriteActors;
    }

    public void setFavouriteActors(Set<String> favouriteActors) {
        this.favouriteActors = favouriteActors;
    }

    public Set<String> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(Set<String> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }
}