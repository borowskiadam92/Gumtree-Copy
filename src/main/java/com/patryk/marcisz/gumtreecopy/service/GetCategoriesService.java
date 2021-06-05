package com.patryk.marcisz.gumtreecopy.service;

import com.patryk.marcisz.gumtreecopy.model.dao.CategoryEntity;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.GetMainCategoriesResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.MainCategoryResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.SubcategoryResponse;
import com.patryk.marcisz.gumtreecopy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class GetCategoriesService {

    private final CategoryRepository categoryRepository;

    public GetMainCategoriesResponse getMainCategories() {
        List<CategoryEntity> mainCategories = categoryRepository.getMainCategories();
        return GetMainCategoriesResponse.builder()
                .categories(mainCategories.stream()
                        .map(convertCategoryEntityToDto())
                        .collect(Collectors.toList()))
                .build();
    }

    private Function<CategoryEntity, MainCategoryResponse> convertCategoryEntityToDto() {
        return category ->
                MainCategoryResponse.builder()
                        .mainCategoryName(category.getName())
                        .subcategories(convertSubcategoryEntityToDto(category))
                        .build();
    }

    private List<SubcategoryResponse> convertSubcategoryEntityToDto(CategoryEntity category) {
        return category.getChildren()
                .stream()
                .map(subcategory -> SubcategoryResponse.builder().subcategoryName(subcategory.getName()).build())
                .collect(Collectors.toList());
    }
}
