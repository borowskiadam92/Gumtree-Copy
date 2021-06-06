package com.patryk.marcisz.gumtreecopy.service;

import com.patryk.marcisz.gumtreecopy.converters.OfferResponseConverter;
import com.patryk.marcisz.gumtreecopy.model.dao.CategoryEntity;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.CategoryOffersResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.GetMainCategoriesResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.MainCategoryResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.SubcategoryResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.offer.OfferResponse;
import com.patryk.marcisz.gumtreecopy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class GetCategoriesService {

    private final CategoryRepository categoryRepository;
    private final OfferResponseConverter offerResponseConverter;

    public GetMainCategoriesResponse getMainCategories() {
        List<CategoryEntity> mainCategories = categoryRepository.getMainCategories();
        return GetMainCategoriesResponse.builder()
                .categories(mainCategories.stream()
                        .map(convertCategoryEntityToDto())
                        .collect(Collectors.toList()))
                .build();
    }

    public CategoryOffersResponse getOffersForCategory(String categoryName) {
        ArrayList<CategoryEntity> subCategories = new ArrayList<>();
        getSubCategories(findCategoryByCategoryName(categoryName), subCategories);
        List<OfferResponse> offers = subCategories.stream().flatMap(category -> category.getOffers().stream())
                .map(offerResponseConverter::toDto)
                .collect(Collectors.toList());
        return CategoryOffersResponse.builder().category(categoryName).offers(offers).build();
    }

    public void getSubCategories(CategoryEntity entity, List<CategoryEntity> categories){
        categories.add(entity);
        for(CategoryEntity children : entity.getChildren()){
            getSubCategories(children, categories);
        }
    }

    public MainCategoryResponse getSubcategoriesForCategory(String categoryName) {
        CategoryEntity entity = findCategoryByCategoryName(categoryName);
        return convertCategoryEntityToDto().apply(entity);
    }

    private CategoryEntity findCategoryByCategoryName(String categoryName) {
        return categoryRepository.findAll().stream()  //nie jest to najwydajniejsze rozwiazanie na swiecie, trzeba bedzie per categoryId chyba ;)
                .filter(category -> StringUtils.stripAccents(category.getName()).replaceAll("\\s+", "-").toLowerCase().equals(categoryName))
                .findAny().orElseThrow(() -> new RuntimeException("category not found"));
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
