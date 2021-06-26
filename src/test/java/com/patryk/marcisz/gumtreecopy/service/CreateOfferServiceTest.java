package com.patryk.marcisz.gumtreecopy.service;

import com.patryk.marcisz.gumtreecopy.converters.OfferRequestConverter;
import com.patryk.marcisz.gumtreecopy.model.dto.offer.CreateOfferRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateOfferServiceTest {

    @Mock
    private OfferRequestConverter converter;

    @InjectMocks
    private CreateOfferService createOfferService;

    @Test
    void shouldPassRequestObjectToConverter(){
        CreateOfferRequest createOfferRequest = CreateOfferRequest.builder()
                .categoryId(1L)
                .title("example title")
                .content("example content")
                .price(1200)
                .build();

        createOfferService.createOffer(createOfferRequest);
        verify(converter).convertAndPersist(createOfferRequest);
    }

}