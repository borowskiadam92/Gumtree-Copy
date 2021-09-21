package com.patryk.marcisz.gumtreecopy.service;

import com.patryk.marcisz.gumtreecopy.exceptions.AppErrorMessage;
import com.patryk.marcisz.gumtreecopy.exceptions.GumtreeCopyApiException;
import com.patryk.marcisz.gumtreecopy.model.UserResponse;
import com.patryk.marcisz.gumtreecopy.model.dao.UserActivationEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import com.patryk.marcisz.gumtreecopy.model.dto.users.CreateUserRequest;
import com.patryk.marcisz.gumtreecopy.repository.AuthorityRepository;
import com.patryk.marcisz.gumtreecopy.repository.UserActivationRepository;
import com.patryk.marcisz.gumtreecopy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserActivationRepository userActivationRepository;
    private final EmailService emailService;

    @Value("${spring.mail.username}")
    private String senderMail;

    @Transactional
    public void createUser(CreateUserRequest request) {
        userRepository.findByMailOrNick(request.getMail()).ifPresent((x) -> {
            throw new GumtreeCopyApiException(AppErrorMessage.USER_EXIST);
        });

        Function<CreateUserRequest, UserEntity> convertDtoToDao = (CreateUserRequest user) -> UserEntity.builder()
                .mail(user.getMail())
                .password(passwordEncoder.encode(user.getPassword()))
                .nick(request.getMail())
                .authorities(List.of(authorityRepository.findByName("ROLE_USER").orElseThrow(() -> new GumtreeCopyApiException(AppErrorMessage.MISSING_AUTHORITY, "ROLE_USER"))))
                .build();

        UserEntity savedUser = userRepository.save(convertDtoToDao.apply(request));
        UserActivationEntity newToken = userActivationRepository.save(UserActivationEntity.builder()
                .activationToken(UUID.randomUUID().toString())
                .user(savedUser)
                .build()
        );

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(savedUser.getMail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom(senderMail);
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8080/api/activate-user/" + newToken.getActivationToken());

        emailService.sendEmail(mailMessage);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public UserResponse getUser(String userName) {
        return userRepository.findByMailOrNick(userName).map(this::toDto).orElseThrow();
    }

    private UserResponse toDto(UserEntity entity) {
        return UserResponse.builder().mail(entity.getMail()).nick(entity.getNick()).build();
    }

    @Transactional
    public void activateUser(String token) {
        UserActivationEntity userActivationEntity = userActivationRepository.findByActivationToken(token).orElseThrow();
        userActivationEntity.getUser().setActive(true);
        userActivationRepository.delete(userActivationEntity);
    }


}
