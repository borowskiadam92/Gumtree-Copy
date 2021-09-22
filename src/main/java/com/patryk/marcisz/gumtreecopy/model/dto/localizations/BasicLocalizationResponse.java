package com.patryk.marcisz.gumtreecopy.model.dto.localizations;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BasicLocalizationResponse {
    private String name;
    private String searchableName;
}
