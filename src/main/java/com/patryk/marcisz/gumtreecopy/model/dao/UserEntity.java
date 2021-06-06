package com.patryk.marcisz.gumtreecopy.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String mail;

    @Column(unique = true)
    private String nick;

    private String password;

    @ManyToMany
    private List<AuthorityEntity> authorities;

    @OneToMany
    private List<OfferEntity> offers;
}
