package com.patryk.marcisz.gumtreecopy.model.dao;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long parent;

}
