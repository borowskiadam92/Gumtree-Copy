package com.patryk.marcisz.gumtreecopy.controller;

import com.patryk.marcisz.gumtreecopy.model.dto.offer.CreateOfferRequest;
import com.patryk.marcisz.gumtreecopy.model.dto.offer.OfferResponse;
import com.patryk.marcisz.gumtreecopy.service.CreateOfferService;
import com.patryk.marcisz.gumtreecopy.service.GetOffersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.Principal;

@RestController
@RequestMapping(value = "/api/offers")
@RequiredArgsConstructor
public class OffersController {

    private final GetOffersService getOffersService;
    private final CreateOfferService createOfferService;

    @GetMapping(path = "/{id}")
    public OfferResponse getOfferById(@PathVariable(name = "id") BigInteger id){
            return getOffersService.getOfferById(id);
    }

    @PostMapping
    public void createOffer(@RequestBody CreateOfferRequest createOfferRequest){
        createOfferService.createOffer(createOfferRequest);
    }

}
