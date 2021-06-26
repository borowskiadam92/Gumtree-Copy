package com.patryk.marcisz.gumtreecopy.service;

import com.patryk.marcisz.gumtreecopy.converters.OfferResponseConverter;
import com.patryk.marcisz.gumtreecopy.model.dao.CategoryEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.OfferEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import com.patryk.marcisz.gumtreecopy.model.dto.offer.OfferResponse;
import com.patryk.marcisz.gumtreecopy.repository.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetOffersServiceTest {

    private GetOffersService getOffersService;

    @Mock
    private OfferRepository offerRepository;

    @Spy //zachowuje zachowanie oryginalnego obiektu
    private OfferResponseConverter offerResponseConverter;

    @BeforeEach
    void setUp(){
        getOffersService = new GetOffersService(offerRepository, offerResponseConverter);
    }

    @Test
    void shouldReturnOfferWhenExist(){
        when(offerRepository.findById(BigInteger.ONE)).thenReturn(Optional.of(OfferEntity.builder()
                .id(BigInteger.ONE)
                .price(1200)
                .creator(UserEntity.builder().id(1L).build())
                .content("offer content")
                .publishDate(LocalDate.of(2020, 6, 6))
                .category(CategoryEntity.builder().id(1L).build())
                .title("offer x")
                .build()));
        OfferResponse offerById = getOffersService.getOfferById(BigInteger.ONE);

        assertEquals(1L, offerById.getCategoryId());
        assertEquals("offer content", offerById.getContent());
        assertEquals("offer x", offerById.getTitle());
        assertEquals("20200606", offerById.getPublishDate());
        assertEquals(BigInteger.ONE, offerById.getId());
        assertEquals(1L, offerById.getCreatorId());
        assertEquals(1200, offerById.getPrice());
    }

    @Test
    void shouldThrowExceptionWhenMissingId(){
        when(offerRepository.findById(any())).thenThrow(new RuntimeException("offer not found"));

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> getOffersService.getOfferById(BigInteger.ONE));
        assertEquals("offer not found", runtimeException.getMessage());
    }
}