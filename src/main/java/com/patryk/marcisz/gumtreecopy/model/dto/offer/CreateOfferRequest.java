package com.patryk.marcisz.gumtreecopy.model.dto.offer;

import lombok.Data;

@Data
public class CreateOfferRequest {
    private String title;
    private String content;
    private Integer price;
    private Long categoryId;
}
