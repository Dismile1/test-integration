package com.ratiose.testtask.service.impl;

import com.ratiose.testtask.entity.User;
import com.ratiose.testtask.exception.BusinessException;
import com.ratiose.testtask.exception.DuplicateEmailException;
import com.ratiose.testtask.repository.UserRepository;
import com.ratiose.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(User user) {
        User foundUser = userRepository.findByEmail(user.getEmail());
        if (Objects.nonNull(foundUser)) {
            throw new DuplicateEmailException("User with such email already exist");
        }
        encryptPassword(user);
        userRepository.save(user);
    }

    private void encryptPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @Override
    public void addFavouriteActor(String actorId) {
        User user = getCurrentUser();

        Set<String> favouriteActors = user.getFavouriteActors();
        if (favouriteActors.contains(actorId)) {
            throw new BusinessException("Actor was already added to favourites");
        }
        favouriteActors.add(actorId);
        userRepository.save(user);
    }

    @Override
    public void removeFavouriteActor(String actorId) {
        User user = getCurrentUser();

        Set<String> favouriteActors = user.getFavouriteActors();
        if (!favouriteActors.contains(actorId)) {
            throw new BusinessException("Actor is not in favourites list");
        }
        favouriteActors.remove(actorId);
        userRepository.save(user);
    }

    @Override
    public void markMovieWatched(String movieId) {
        User user = getCurrentUser();

        Set<String> watchedMovies = user.getWatchedMovies();
        if (watchedMovies.contains(movieId)) {
            throw new BusinessException("Movie is already added to watched list");
        }
        watchedMovies.add(movieId);
        userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        // @formatter:off
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .filter(org.springframework.security.core.userdetails.User.class::isInstance)
                .map(org.springframework.security.core.userdetails.User.class::cast)
                .map(org.springframework.security.core.userdetails.User::getUsername)
                .map(userRepository::findByEmail)
                .orElse(null);
        // @formatter:on
    }
}