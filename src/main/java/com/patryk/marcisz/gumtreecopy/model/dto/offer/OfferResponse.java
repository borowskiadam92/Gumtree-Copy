package com.patryk.marcisz.gumtreecopy.model.dto.offer;

import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;

@Builder
@Getter
public class OfferResponse {
    private BigInteger id;
    private String title;
    private long price;
    private long creatorId;
    private long categoryId;
    private String publishDate;
    private String content;
}
