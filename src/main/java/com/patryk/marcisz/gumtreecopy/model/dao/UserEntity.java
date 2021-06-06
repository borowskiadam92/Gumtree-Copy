package com.patryk.marcisz.gumtreecopy.model.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mail;

    private String nick;

    private String password;

    @ManyToMany
    private List<AuthorityEntity> authorities;

}
