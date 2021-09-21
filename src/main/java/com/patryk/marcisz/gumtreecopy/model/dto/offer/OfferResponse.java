package com.patryk.marcisz.gumtreecopy.model.dto.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OfferResponse {
    private BigInteger id;
    private String title;
    private int price;
    private long creatorId;
    private long categoryId;
    private String publishDate;
    private String content;
    private String localization;
}
