package com.patryk.marcisz.gumtreecopy.repository;

import com.patryk.marcisz.gumtreecopy.model.dao.UserActivationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserActivationRepository extends JpaRepository<UserActivationEntity, Long> {

    Optional<UserActivationEntity> findByActivationToken(String token);

}
