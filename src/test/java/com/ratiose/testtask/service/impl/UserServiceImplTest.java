package com.ratiose.testtask.service.impl;


import com.ratiose.testtask.entity.User;
import com.ratiose.testtask.exception.DuplicateEmailException;
import com.ratiose.testtask.repository.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String SOME_EMAIL = "sda";
    private static final String SOME_PASSWORD = "pass";
    private static final String ENDODED_PASSWORD = "asdasda";

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private User mockUser;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() throws Exception {
        when(mockUser.getEmail()).thenReturn(SOME_EMAIL);
        when(mockUser.getPassword()).thenReturn(SOME_PASSWORD);
        when(mockPasswordEncoder.encode(SOME_PASSWORD)).thenReturn(ENDODED_PASSWORD);
    }

    @Test
    public void shouldThrowDuplicateEmailException_whenRegisterUser_andUserWithEmailAlreadyExist() {
        when(mockUserRepository.findByEmail(SOME_EMAIL)).thenReturn(mockUser);
        expectedException.expect(DuplicateEmailException.class);

        userService.registerUser(mockUser);
    }

    @Test
    public void shouldEncodePassword_whenRegisterUser() {
        userService.registerUser(mockUser);

        verify(mockUser).setPassword(ENDODED_PASSWORD);
    }

    @Test
    public void shouldSaveUser_whenRegisterUser() {
        userService.registerUser(mockUser);

        verify(mockUserRepository).save(mockUser);
    }
}