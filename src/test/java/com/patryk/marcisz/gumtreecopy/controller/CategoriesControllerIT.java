package com.patryk.marcisz.gumtreecopy.controller;

import com.patryk.marcisz.gumtreecopy.exceptions.AppErrorMessage;
import com.patryk.marcisz.gumtreecopy.exceptions.GumtreeCopyApiException;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.CategoryOffersResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.GetMainCategoriesResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.MainCategoryResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.SubcategoryResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.offer.OfferResponse;
import com.patryk.marcisz.gumtreecopy.service.GetCategoriesService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigInteger;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;


/* test integracyjny, co oznacza ze context springa wstaje, @WebMvcTest oznacza
że procesowi poddana jest tylko warstwa web, wstrzykniecie @Service spowoduje
problemy podczas wstrzykiwania */
@WebMvcTest(CategoriesController.class)
class CategoriesControllerIT {

    @MockBean
    private GetCategoriesService getCategoriesService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnMainCategories() throws Exception {
        when(getCategoriesService.getMainCategories()).thenReturn(
                GetMainCategoriesResponse.builder()
                        .categories(List.of(
                                MainCategoryResponse.builder()
                                        .mainCategoryName("category_1")
                                        .subcategories(List.of(
                                                SubcategoryResponse.builder().subcategoryName("subcat_1_1").build(),
                                                SubcategoryResponse.builder().subcategoryName("subcat_1_2").build(),
                                                SubcategoryResponse.builder().subcategoryName("subcat_1_3").build()
                                        ))
                                        .build(),
                                MainCategoryResponse.builder()
                                        .mainCategoryName("category_2")
                                        .subcategories(List.of(
                                                SubcategoryResponse.builder().subcategoryName("subcat_2_1").build(),
                                                SubcategoryResponse.builder().subcategoryName("subcat_2_2").build(),
                                                SubcategoryResponse.builder().subcategoryName("subcat_2_3").build()
                                        ))
                                        .build()
                        )).build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories.length()", equalTo(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0].mainCategoryName", equalTo("category_1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0].subcategories[0].subcategoryName", equalTo("subcat_1_1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0].subcategories[1].subcategoryName", equalTo("subcat_1_2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0].subcategories[2].subcategoryName", equalTo("subcat_1_3")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[1].mainCategoryName", equalTo("category_2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[1].subcategories[0].subcategoryName", equalTo("subcat_2_1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[1].subcategories[1].subcategoryName", equalTo("subcat_2_2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[1].subcategories[2].subcategoryName", equalTo("subcat_2_3")))

                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldReturnSubcategoriesForSpecifiedCategory() throws Exception {
        when(getCategoriesService.getSubcategoriesForCategory("nieruchomosci"))
                .thenReturn(MainCategoryResponse.builder()
                        .mainCategoryName("Nieruchomości")
                        .subcategories(List.of(
                                SubcategoryResponse.builder().subcategoryName("Wynajem").build(),
                                SubcategoryResponse.builder().subcategoryName("Zakup").build(),
                                SubcategoryResponse.builder().subcategoryName("Sprzedaż").build()))
                        .build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/nieruchomosci"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mainCategoryName", equalTo("Nieruchomości")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subcategories[0].subcategoryName", equalTo("Wynajem")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subcategories[1].subcategoryName", equalTo("Zakup")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subcategories[2].subcategoryName", equalTo("Sprzedaż")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldReturnOffersForSpecifiedCategory() throws Exception {
        when(getCategoriesService.getOffersForCategory("nieruchomosci"))
                .thenReturn(CategoryOffersResponse.builder()
                        .category("Nieruchomości")
                        .offers(List.of(
                                OfferResponse.builder()
                                        .id(BigInteger.ONE)
                                        .categoryId(1)
                                        .title("flip")
                                        .content("taniej kupie drozej sprzedam")
                                        .publishDate("2020-01-01")
                                        .price(15000000)
                                        .build()
                        ))
                        .build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/nieruchomosci/offers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category", equalTo("Nieruchomości")))
                .andExpect(MockMvcResultMatchers.jsonPath("offers[0].id", equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("offers[0].title", equalTo("flip")))
                .andExpect(MockMvcResultMatchers.jsonPath("offers[0].price", equalTo(15000000)))
                .andExpect(MockMvcResultMatchers.jsonPath("offers[0].creatorId", equalTo(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("offers[0].categoryId", equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("offers[0].publishDate", equalTo("2020-01-01")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldParseErrorResponseWhenException() throws Exception {
        when(getCategoriesService.getOffersForCategory(any())).thenThrow(new GumtreeCopyApiException(AppErrorMessage.MISSING_CATEGORY, "nieruchomosci"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/nieruchomosci/offers"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", equalTo("Category 'nieruchomosci' not found")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorTime", Matchers.notNullValue()))
                .andDo(MockMvcResultHandlers.print());
    }
}