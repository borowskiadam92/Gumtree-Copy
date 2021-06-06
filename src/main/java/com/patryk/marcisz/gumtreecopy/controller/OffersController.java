package com.patryk.marcisz.gumtreecopy.controller;

import com.patryk.marcisz.gumtreecopy.model.dto.offer.OfferResponse;
import com.patryk.marcisz.gumtreecopy.service.GetOffersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping(value = "/api/offers")
@RequiredArgsConstructor
public class OffersController {

    private final GetOffersService getOffersService;

    @GetMapping(path = "/{id}")
    public OfferResponse getOfferById(@PathVariable(name = "id") BigInteger id){
            return getOffersService.getOfferById(id);
    }

}
