package com.patryk.marcisz.gumtreecopy.service;

import com.patryk.marcisz.gumtreecopy.model.dto.categories.main.CategoryDetailsResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.localizations.BasicLocalizationResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.localizations.GetMainLocalizationsResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.localizations.LocalizationDetailsResponse;
import com.patryk.marcisz.gumtreecopy.repository.LocalizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocalizationService {

    private final LocalizationRepository localizationRepository;

    public GetMainLocalizationsResponse getMainLocalizations() {
        List<LocalizationDetailsResponse> response = localizationRepository.findAllChildrenOfMainCategory()
                .stream()
                .map(localizationEntity -> LocalizationDetailsResponse.builder()
                        .current(BasicLocalizationResponse.builder()
                                .name(localizationEntity.getName())
                                .searchableName(localizationEntity.getSearchableName())
                                .build())
                        .childrens(localizationEntity.getChildren().stream()
                                .map(subLoc -> BasicLocalizationResponse.builder()
                                        .name(subLoc.getName())
                                        .searchableName(subLoc.getSearchableName())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        return GetMainLocalizationsResponse.builder().localizations(response).build();
    }

    public CategoryDetailsResponse getChildrenFor(String categoryName) {
        return null;
    }
}
