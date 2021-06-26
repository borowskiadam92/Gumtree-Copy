package com.patryk.marcisz.gumtreecopy.repository;

import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = "/sql/authorities-and-users.sql")
class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindBySameMailOrNick() {
        Optional<UserEntity> commonUser = userRepository.findByMailOrNick("commonUser");
        assertEquals(1000, commonUser.get().getId());
        assertEquals("user@user.pl", commonUser.get().getMail());
        assertEquals("commonUser", commonUser.get().getNick());
        assertEquals(1000, commonUser.get().getAuthorities().get(0).getId());
        assertEquals("ROLE_USER", commonUser.get().getAuthorities().get(0).getName());
    }

    //ten test moglby byc lepszy,
    // np. zakladajacy ze raz szukamy po wartosci A,
    // raz po wartosci B
    @Test
    void shouldFindByDifferentMailOrNick() {
        Optional<UserEntity> commonUser = userRepository.findByMailOrNick("user@user.pl", "commonUser");
        assertEquals(1000, commonUser.get().getId());
        assertEquals("user@user.pl", commonUser.get().getMail());
        assertEquals("commonUser", commonUser.get().getNick());
        assertEquals(1000, commonUser.get().getAuthorities().get(0).getId());
        assertEquals("ROLE_USER", commonUser.get().getAuthorities().get(0).getName());
    }

}