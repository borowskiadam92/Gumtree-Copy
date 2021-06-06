package com.patryk.marcisz.gumtreecopy.converters;

import com.patryk.marcisz.gumtreecopy.model.dao.OfferEntity;
import com.patryk.marcisz.gumtreecopy.model.dto.offer.OfferResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class OfferResponseConverter implements Converter<OfferEntity, OfferResponse> {

    @Override
    public OfferResponse toDto(OfferEntity offerEntity) {
        return OfferResponse.builder()
                .id(offerEntity.getId())
                .title(offerEntity.getTitle())
                .price(offerEntity.getPrice())
                .creatorId(offerEntity.getCreator().getId())
                .categoryId(offerEntity.getCategory().getId())
                .publishDate(offerEntity.getPublishDate().format(DateTimeFormatter.BASIC_ISO_DATE))
                .content(offerEntity.getContent())
                .build();
    }

    @Override
    public OfferEntity toDao(OfferResponse offerResponse) {
        throw new UnsupportedOperationException();
    }

}
