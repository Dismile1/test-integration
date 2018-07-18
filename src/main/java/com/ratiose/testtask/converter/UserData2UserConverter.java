package com.ratiose.testtask.converter;

import com.ratiose.testtask.dto.UserData;
import com.ratiose.testtask.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserData2UserConverter implements Converter<UserData, User> {

    @Override
    public User convert(UserData userData) {
        User user = new User();
        user.setEmail(userData.getEmail());
        user.setPassword(userData.getPassword());
        return user;
    }
}
