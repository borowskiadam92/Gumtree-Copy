package com.patryk.marcisz.gumtreecopy.repository;

import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
