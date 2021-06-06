package com.patryk.marcisz.gumtreecopy.model.dto.categories;

import com.patryk.marcisz.gumtreecopy.model.dto.offer.OfferResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CategoryOffersResponse {
    private String category;
    private List<OfferResponse> offers;
}
