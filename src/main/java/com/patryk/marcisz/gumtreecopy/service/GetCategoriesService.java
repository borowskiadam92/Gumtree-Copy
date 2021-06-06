package com.patryk.marcisz.gumtreecopy.service;

import com.patryk.marcisz.gumtreecopy.model.dao.CategoryEntity;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.GetMainCategoriesResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.MainCategoryResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.SubcategoryResponse;
import com.patryk.marcisz.gumtreecopy.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class GetCategoriesService {

    private final CategoriesRepository categoriesRepository;

    public GetMainCategoriesResponse getMainCategories() {
        List<CategoryEntity> mainCategories = categoriesRepository.getMainCategories();
        return GetMainCategoriesResponse.builder()
                .categories(mainCategories.stream()
                        .map(convertCategoryEntityToDto())
                        .collect(Collectors.toList()))
                .build();
    }

    public MainCategoryResponse getSubcategoriesForCategory(String categoryName) {
        CategoryEntity entity = categoriesRepository.findAll().stream()  //nie jest to najwydajniejsze rozwiazanie na swiecie, trzeba bedzie per categoryId chyba ;)
                .filter(category -> StringUtils.stripAccents(category.getName()).replaceAll("\\s+", "-").toLowerCase().equals(categoryName))
                .findAny()
                .orElseThrow(() -> new RuntimeException("category not found"));
        return convertCategoryEntityToDto().apply(entity);
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
