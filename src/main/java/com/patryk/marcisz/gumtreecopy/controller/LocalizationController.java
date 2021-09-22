package com.patryk.marcisz.gumtreecopy.controller;

import com.patryk.marcisz.gumtreecopy.model.dto.localizations.GetMainLocalizationsResponse;
import com.patryk.marcisz.gumtreecopy.service.LocalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/localizations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class LocalizationController {

    private final LocalizationService localizationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GetMainLocalizationsResponse getMainCategories(){
        return localizationService.getMainLocalizations();
    }

//    @GetMapping(value = "/{categoryName}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public CategoryDetailsResponse getSubcategoriesForCategory(@PathVariable String categoryName){
//        return localizationService.getChildrenFor(categoryName);
//    }

//    @GetMapping(value = "/{categoryName}/offers", produces = MediaType.APPLICATION_JSON_VALUE)
//    public CategoryOffersResponse getOffersForCategories(@PathVariable String categoryName){
//        return localizationService.getOffersForCategory(categoryName);
//    }

}
