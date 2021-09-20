package com.patryk.marcisz.gumtreecopy.repository;

import com.patryk.marcisz.gumtreecopy.model.dao.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryIT {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Sql(value = "/sql/category-and-offers.sql")
    void shouldFindRoleByName() {
        String countQuery = "select count(*) from categories";
        Object startCount = entityManager.createNativeQuery(countQuery).getSingleResult();
        assertEquals(BigInteger.valueOf(3), startCount);

        List<CategoryEntity> categories = categoryRepository.findAllChildrenOfMainCategory();
        assertEquals(1, categories.size());
        assertEquals("Nieruchomości", categories.get(0).getName());
        assertEquals(1000, categories.get(0).getId());
        assertEquals(2, categories.get(0).getChildren().size());
        assertEquals("Sprzedaż", categories.get(0).getChildren().get(0).getName());
        assertEquals(1001, categories.get(0).getChildren().get(0).getId());
        assertEquals("Zakup", categories.get(0).getChildren().get(1).getName());
        assertEquals(1002, categories.get(0).getChildren().get(1).getId());
        assertEquals(categories.get(0), categories.get(0).getChildren().get(0).getParent());
        assertEquals(categories.get(0), categories.get(0).getChildren().get(1).getParent());


    }

}
