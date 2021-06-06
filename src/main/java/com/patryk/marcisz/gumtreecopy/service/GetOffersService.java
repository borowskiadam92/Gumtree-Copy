package com.patryk.marcisz.gumtreecopy.service;

import com.patryk.marcisz.gumtreecopy.converters.OfferResponseConverter;
import com.patryk.marcisz.gumtreecopy.model.dto.offer.OfferResponse;
import com.patryk.marcisz.gumtreecopy.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class GetOffersService {

    private final OfferRepository offerRepository;
    private final OfferResponseConverter offerResponseConverter;

    public OfferResponse getOfferById(BigInteger id) {
        return offerRepository.findById(id)
                .map(offerResponseConverter::toDto)
                .orElseThrow(() -> new RuntimeException("offer not found"));
    }
}
