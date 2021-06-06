package com.patryk.marcisz.gumtreecopy.service;

import com.patryk.marcisz.gumtreecopy.model.dto.offer.OfferResponse;
import com.patryk.marcisz.gumtreecopy.repository.OffersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class GetOffersService {

    private final OffersRepository offersRepository;

    public OfferResponse getOfferById(BigInteger id) {
        return offersRepository.findById(id)
                .map(offer -> OfferResponse.builder()
                        .id(offer.getId())
                        .title(offer.getTitle())
                        .price(offer.getPrice())
                        .categoryId(offer.getCategory().getId())
                        .publishDate(offer.getPublishDate().format(DateTimeFormatter.BASIC_ISO_DATE))
                        .description(offer.getContent())
                        .build())
                .orElseThrow(() -> new RuntimeException("offer not found"));
    }
}
