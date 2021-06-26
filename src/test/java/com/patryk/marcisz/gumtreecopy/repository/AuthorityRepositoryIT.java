package com.patryk.marcisz.gumtreecopy.repository;

import com.patryk.marcisz.gumtreecopy.model.dao.AuthorityEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;

import java.math.BigInteger;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorityRepositoryIT {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    void shouldSaveAuthorityToDatabase(){
        String countQuery = "select count(*) from authorities where name like 'ROLE_NEW'";
        Object startCount = entityManager.createNativeQuery(countQuery).getSingleResult();
        assertEquals(BigInteger.ZERO, startCount);

        authorityRepository.saveAndFlush(AuthorityEntity.builder().name("ROLE_NEW").build());

        Object finalCount = entityManager.createNativeQuery(countQuery).getSingleResult();
        assertEquals(BigInteger.ONE, finalCount);
    }

    @Test
    @Sql(value = "/sql/authorities-and-users.sql")
    void shouldFindRoleByName(){
        Optional<AuthorityEntity> userRole = authorityRepository.findByName("ROLE_USER");
        assertEquals("ROLE_USER", userRole.get().getName());
        assertEquals(1000L, userRole.get().getId());
    }

}