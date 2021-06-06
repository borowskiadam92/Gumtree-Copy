package com.patryk.marcisz.gumtreecopy.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "offers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String title;
    @ManyToOne
    private CategoryEntity category;
    private LocalDate publishDate;
    private String content;
    private Integer price;

}
