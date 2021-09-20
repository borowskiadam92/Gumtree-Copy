package com.patryk.marcisz.gumtreecopy.service;

import com.patryk.marcisz.gumtreecopy.converters.OfferResponseConverter;
import com.patryk.marcisz.gumtreecopy.exceptions.GumtreeCopyApiException;
import com.patryk.marcisz.gumtreecopy.model.dao.CategoryEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.OfferEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.CategoryOffersResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.GetMainCategoriesResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.CategoryDetailsResponse;
import com.patryk.marcisz.gumtreecopy.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCategoriesServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private OfferResponseConverter offerResponseConverter;

    @InjectMocks
    private GetCategoriesService getCategoriesService;

    private final OfferEntity exampleOffer = OfferEntity.builder()
            .id(BigInteger.ONE)
            .price(1200)
            .creator(UserEntity.builder().id(1L).build())
            .content("offer content")
            .publishDate(LocalDate.of(2020, 6, 6))
            .category(CategoryEntity.builder().id(1L).build())
            .title("offer x")
            .build();

    private final List<CategoryEntity> categories = List.of(
            CategoryEntity.builder().name("Nieruchomości").offers(emptyList()).children(
                    List.of(
                            CategoryEntity.builder().name("Wynajem").children(emptyList())
                                    .offers(singletonList(exampleOffer)).build(),
                            CategoryEntity.builder().name("Sprzedaż").children(emptyList()).offers(emptyList()).build()))
                    .build(),
            CategoryEntity.builder().name("AGD/RTV").children(emptyList()).offers(emptyList()).build(),
            CategoryEntity.builder().name("Elektronika").children(emptyList()).offers(emptyList()).build());

    @Test
    void shouldReturnMainCategories() {
        when(categoryRepository.findAllChildrenOfMainCategory()).thenReturn(categories);

        GetMainCategoriesResponse mainCategories = getCategoriesService.getMainCategories();

        assertEquals(3, mainCategories.getCategories().size());
        assertEquals("Nieruchomości", mainCategories.getCategories().get(0).getCategoryName());
        assertEquals("Wynajem", mainCategories.getCategories().get(0).getSubcategories().get(0).getName());
        assertEquals("Sprzedaż", mainCategories.getCategories().get(0).getSubcategories().get(1).getName());
        assertEquals("AGD/RTV", mainCategories.getCategories().get(1).getCategoryName());
        assertEquals("Elektronika", mainCategories.getCategories().get(2).getCategoryName());
    }

    @Test
    void shouldReturnSubcategoriesForCategory() {
        CategoryEntity categories = CategoryEntity.builder().name("Nieruchomości").offers(emptyList()).children(
                List.of(
                        CategoryEntity.builder().name("Wynajem").children(emptyList())
                                .offers(singletonList(exampleOffer)).build(),
                        CategoryEntity.builder().name("Sprzedaż").children(emptyList()).offers(emptyList()).build()))
                .build();

        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(categories));
        CategoryDetailsResponse subcategories = getCategoriesService.getSubcategoriesForCategory("nieruchomosci");

        assertEquals("Nieruchomości", subcategories.getCategoryName());
        assertEquals(2, subcategories.getSubcategories().size());
        assertEquals("Wynajem", subcategories.getSubcategories().get(0).getName());
        assertEquals("Sprzedaż", subcategories.getSubcategories().get(1).getName());
    }

    @Test
    void shouldThrowExceptionForSubcategoriesWhenCategoryNotFound() {
        assertThrows(GumtreeCopyApiException.class,
                () -> getCategoriesService.getSubcategoriesForCategory("nieruchomosci"));
    }

    @Test
    void shouldReturnOffersForCategory() {
        when(categoryRepository.findAll()).thenReturn(categories);

        CategoryOffersResponse nieruchomosci = getCategoriesService.getOffersForCategory("nieruchomosci");
        assertEquals("Nieruchomości", nieruchomosci.getCategory());
        assertEquals(1, nieruchomosci.getOffers().size());
        assertEquals(BigInteger.ONE, nieruchomosci.getOffers().get(0).getId());
        assertEquals("offer x", nieruchomosci.getOffers().get(0).getTitle());
        assertEquals("offer content", nieruchomosci.getOffers().get(0).getContent());
        assertEquals(1200, nieruchomosci.getOffers().get(0).getPrice());
        assertEquals(1, nieruchomosci.getOffers().get(0).getCategoryId());
        assertEquals(1, nieruchomosci.getOffers().get(0).getCreatorId());
    }

    @Test
    void shouldThrowExceptionForOffersWhenCategoryNotFound() {
        assertThrows(GumtreeCopyApiException.class,
                () -> getCategoriesService.getOffersForCategory("nieruchomosci"));
    }

}
