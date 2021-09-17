package com.patryk.marcisz.gumtreecopy.model.dto.categories.main;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MainCategoryResponse {

    private String mainCategoryName;
    private String searchableName;
    private List<SubcategoryResponse> subcategories;

}
