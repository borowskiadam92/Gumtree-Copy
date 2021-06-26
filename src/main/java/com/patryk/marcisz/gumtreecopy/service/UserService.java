package com.patryk.marcisz.gumtreecopy.service;

import com.patryk.marcisz.gumtreecopy.exceptions.AppErrorMessage;
import com.patryk.marcisz.gumtreecopy.exceptions.GumtreeCopyApiException;
import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import com.patryk.marcisz.gumtreecopy.model.dto.users.CreateUserRequest;
import com.patryk.marcisz.gumtreecopy.repository.AuthorityRepository;
import com.patryk.marcisz.gumtreecopy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(CreateUserRequest request) {
        userRepository.findByMailOrNick(request.getMail(), request.getNick()).ifPresent((x) -> {
            throw new GumtreeCopyApiException(AppErrorMessage.USER_EXIST);
        });

        Function<CreateUserRequest, UserEntity> convertDtoToDao = (CreateUserRequest user) -> UserEntity.builder()
                .mail(user.getMail())
                .password(passwordEncoder.encode(user.getPassword()))
                .nick(request.getNick())
                .authorities(List.of(authorityRepository.findByName("ROLE_USER").orElseThrow(() -> new GumtreeCopyApiException(AppErrorMessage.MISSING_AUTHORITY, "ROLE_USER"))))
                .build();
        userRepository.save(convertDtoToDao.apply(request));
    }

}
