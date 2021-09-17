package com.patryk.marcisz.gumtreecopy.repository;

import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("select user from UserEntity user " +
            "where user.mail = ?1 or user.nick = ?1")
    Optional<UserEntity> findByMailOrNick(String email);

    @Query
    Optional<UserEntity> findByMailOrNick(String mail, String nick);

}
