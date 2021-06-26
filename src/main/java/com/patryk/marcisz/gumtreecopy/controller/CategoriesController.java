package com.patryk.marcisz.gumtreecopy.controller;

import com.patryk.marcisz.gumtreecopy.model.dto.categories.CategoryOffersResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.GetMainCategoriesResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.MainCategoryResponse;
import com.patryk.marcisz.gumtreecopy.service.GetCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final GetCategoriesService getCategoriesService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GetMainCategoriesResponse getMainCategories(){
        return getCategoriesService.getMainCategories();
    }

    @GetMapping(value = "/{categoryName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MainCategoryResponse getSubcategoriesForCategory(@PathVariable String categoryName){
        return getCategoriesService.getSubcategoriesForCategory(categoryName);
    }

    @GetMapping(value = "/{categoryName}/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryOffersResponse getOffersForCategories(@PathVariable String categoryName){
        return getCategoriesService.getOffersForCategory(categoryName);
    }

}
