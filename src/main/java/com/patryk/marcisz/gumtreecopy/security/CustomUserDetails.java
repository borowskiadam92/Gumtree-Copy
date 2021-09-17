package com.patryk.marcisz.gumtreecopy.security;

import com.patryk.marcisz.gumtreecopy.model.dao.AuthorityEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import com.patryk.marcisz.gumtreecopy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Primary
@Transactional
public class CustomUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByMailOrNick(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        List<String> userAuthorities = userEntity.getAuthorities().stream().map(AuthorityEntity::getName)
                .collect(Collectors.toList());

        return User.builder()
                .username(userEntity.getMail())
                .password(userEntity.getPassword())
                .authorities(userAuthorities.toArray(String[]::new))
                .build();
    }
}
