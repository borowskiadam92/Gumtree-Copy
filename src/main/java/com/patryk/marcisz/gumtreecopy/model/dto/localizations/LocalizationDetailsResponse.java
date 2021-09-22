package com.patryk.marcisz.gumtreecopy.model.dto.localizations;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class LocalizationDetailsResponse {

    private BasicLocalizationResponse current;
    private List<BasicLocalizationResponse> childrens;
    private List<BasicLocalizationResponse> parents;

}
