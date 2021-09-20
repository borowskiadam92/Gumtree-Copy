package com.patryk.marcisz.gumtreecopy.model.dto.categories.main;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BasicCategoryResponse {

    private String name;
    private String searchableName;

}
