package com.patryk.marcisz.gumtreecopy.service;

import com.patryk.marcisz.gumtreecopy.converters.OfferRequestConverter;
import com.patryk.marcisz.gumtreecopy.model.dto.offer.CreateOfferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateOfferService {

    private final OfferRequestConverter offerRequestConverter;

    public void createOffer(CreateOfferRequest createOfferRequest) {
        offerRequestConverter.convertAndPersist(createOfferRequest);
    }
}
