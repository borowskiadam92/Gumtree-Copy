package com.patryk.marcisz.gumtreecopy.model.dto.categories.main;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubcategoryResponse {

    private String subcategoryName;
    private String searchableName;

}
