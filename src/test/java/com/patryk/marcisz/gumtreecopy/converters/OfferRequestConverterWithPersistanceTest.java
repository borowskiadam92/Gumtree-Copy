package com.patryk.marcisz.gumtreecopy.converters;

import com.patryk.marcisz.gumtreecopy.exceptions.GumtreeCopyApiException;
import com.patryk.marcisz.gumtreecopy.model.dao.CategoryEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.OfferEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import com.patryk.marcisz.gumtreecopy.model.dto.offer.CreateOfferRequest;
import com.patryk.marcisz.gumtreecopy.repository.CategoryRepository;
import com.patryk.marcisz.gumtreecopy.repository.OfferRepository;
import com.patryk.marcisz.gumtreecopy.repository.UserRepository;
import com.patryk.marcisz.gumtreecopy.security.GumtreeCopyUserContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferRequestConverterWithPersistanceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private GumtreeCopyUserContext gumtreeCopyUserContext;

    @InjectMocks
    private OfferRequestConverter converter;

    private final CreateOfferRequest request = CreateOfferRequest.builder()
            .categoryId(1L)
            .title("example Title")
            .content("example content")
            .price(1200)
            .build();

    @Test
    void shouldPassOfferToRepository() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(CategoryEntity.builder()
                .name("Nieruchomosci")
                .build()));

        when(offerRepository.save(any())).thenAnswer(x -> {
            OfferEntity entity = x.getArgument(0);
            entity.setId(BigInteger.TEN);
            return entity;
        });

        when(gumtreeCopyUserContext.getCurrentUserName())
                .thenReturn("user@user.pl");

        when(userRepository.findByMailOrNick("user@user.pl"))
                .thenReturn(Optional.of(UserEntity.builder()
                        .mail("user@user.pl")
                        .build()));

        OfferEntity offerEntity = converter.convertAndPersist(request);

        assertEquals(BigInteger.TEN, offerEntity.getId());
        assertEquals("example content", offerEntity.getContent());
        assertEquals("example Title", offerEntity.getTitle());
        assertEquals("user@user.pl", offerEntity.getCreator().getMail());
        assertEquals(offerEntity, offerEntity.getCreator().getOffers().get(0));
        assertEquals(offerEntity, offerEntity.getCategory().getOffers().get(0));
    }

    @Test
    void shouldThrowExceptionWhenMissingCategory() {
        GumtreeCopyApiException gumtreeCopyApiException = assertThrows(GumtreeCopyApiException.class,
                () -> converter.convertAndPersist(request));
        assertEquals(404, gumtreeCopyApiException.getResponseStatus());
        assertEquals("Category '1' not found", gumtreeCopyApiException.getMessage());
    }

}