package com.patryk.marcisz.gumtreecopy.security;

import com.patryk.marcisz.gumtreecopy.model.dao.AuthorityEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import com.patryk.marcisz.gumtreecopy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetails customUserDetails;

    @Test
    void shouldCreateUserDetailsIfUserInDatabase() {
        when(userRepository.findByMailOrNick("user@user.pl"))
                .thenReturn(Optional.of(UserEntity.builder()
                        .mail("user@user.pl")
                        .password("encryptedPassword")
                        .authorities(List.of(AuthorityEntity.builder().name("ROLE_USER").build()))
                        .build()));

        UserDetails userDetails = customUserDetails.loadUserByUsername("user@user.pl");
        assertThat(userDetails.getUsername(), equalTo("user@user.pl"));
        assertThat(userDetails.getPassword(), equalTo("encryptedPassword"));
        assertThat(userDetails.getAuthorities(), contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByMailOrNick("user@user.pl")).thenThrow(new UsernameNotFoundException("user not found"));
        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetails.loadUserByUsername("user@user.pl"));

    }

}