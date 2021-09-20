package com.patryk.marcisz.gumtreecopy.model.dto.categories.main;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CategoryDetailsResponse {

    private String categoryName;
    private String searchableName;
    private List<BasicCategoryResponse> subcategories;
    private List<BasicCategoryResponse> parents;

}
