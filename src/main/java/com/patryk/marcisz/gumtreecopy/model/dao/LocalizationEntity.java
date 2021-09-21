package com.patryk.marcisz.gumtreecopy.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "localization")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocalizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<OfferEntity> offers;

    @Column(name = "name")
    private String name;

    @Column(name = "searchable_name")
    private String searchableName;

    @ManyToOne(fetch = FetchType.LAZY)
    private LocalizationEntity parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<LocalizationEntity> children;

}
