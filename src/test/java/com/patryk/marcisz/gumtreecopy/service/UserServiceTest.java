package com.patryk.marcisz.gumtreecopy.service;

import com.patryk.marcisz.gumtreecopy.exceptions.GumtreeCopyApiException;
import com.patryk.marcisz.gumtreecopy.model.dao.AuthorityEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import com.patryk.marcisz.gumtreecopy.model.dto.users.CreateUserRequest;
import com.patryk.marcisz.gumtreecopy.repository.AuthorityRepository;
import com.patryk.marcisz.gumtreecopy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<UserEntity> argumentCaptor;

    private final CreateUserRequest createUserRequest = CreateUserRequest.builder()
            .mail("user@user.pl")
            .nick("user123")
            .password("password123")
            .build();

    @Test
    void shouldCreateUserWhenValidRequest(){
        when(userRepository.findByMailOrNick(any(), any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encryptedPassword");
        when(authorityRepository.findByName("ROLE_USER")).thenReturn(Optional.of(AuthorityEntity.builder().name("ROLE_USER").build()));

        userService.createUser(createUserRequest);

        verify(userRepository).save(argumentCaptor.capture());
        UserEntity value = argumentCaptor.getValue();

        assertEquals("ROLE_USER", value.getAuthorities().get(0).getName());
        assertEquals("user@user.pl", value.getMail());
        assertEquals("user123", value.getNick());
        assertEquals("encryptedPassword", value.getPassword());
    }

    @Test
    void shouldThrowExceptionWhenEmailExistsInDatabase(){
        when(userRepository.findByMailOrNick(any(), any())).thenReturn(Optional.of(UserEntity.builder().build()));

        GumtreeCopyApiException gumtreeCopyApiException = assertThrows(GumtreeCopyApiException.class, () -> {
            userService.createUser(createUserRequest);
        });

        assertEquals(404, gumtreeCopyApiException.getResponseStatus());
        assertEquals("User with provided email or nick currently exist", gumtreeCopyApiException.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenMissingRole(){
        GumtreeCopyApiException gumtreeCopyApiException = assertThrows(GumtreeCopyApiException.class, () -> {
            userService.createUser(createUserRequest);
        });

        assertEquals(400, gumtreeCopyApiException.getResponseStatus());
        assertEquals("Authority 'ROLE_USER' not found", gumtreeCopyApiException.getMessage());

    }


}