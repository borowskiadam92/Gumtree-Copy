package com.patryk.marcisz.gumtreecopy.controller;

import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import com.patryk.marcisz.gumtreecopy.model.dto.users.CreateUserRequest;
import com.patryk.marcisz.gumtreecopy.repository.AuthoritiesRepository;
import com.patryk.marcisz.gumtreecopy.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(CreateUserRequest request){
        Function<CreateUserRequest, UserEntity> convertDtoToDao = (CreateUserRequest user) -> UserEntity.builder()
                .mail(user.getMail())
                .password(passwordEncoder.encode(user.getPassword()))
                .nick(request.getNick())
                .authorities(List.of(authoritiesRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("authority not found"))))
                .build();
        usersRepository.save(convertDtoToDao.apply(request));
    }

}
