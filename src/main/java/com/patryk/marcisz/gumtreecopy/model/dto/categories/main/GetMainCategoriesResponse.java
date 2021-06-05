package com.patryk.marcisz.gumtreecopy.model.dto.categories.main;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GetMainCategoriesResponse {

    private List<MainCategoryResponse> categories;

}
