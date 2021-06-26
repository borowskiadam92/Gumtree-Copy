package com.patryk.marcisz.gumtreecopy.model.dto.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOfferRequest {
    private String title;
    private String content;
    private Integer price;
    private Long categoryId;
}
