package com.patryk.marcisz.gumtreecopy.model.dto.localizations;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetMainLocalizationsResponse {
    private List<LocalizationDetailsResponse> localizations;
}
