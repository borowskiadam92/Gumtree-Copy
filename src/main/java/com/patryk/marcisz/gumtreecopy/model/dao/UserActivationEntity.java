package com.patryk.marcisz.gumtreecopy.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_activation")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserActivationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activation_token")
    private String activationToken;

    @OneToOne
    private UserEntity user;

}
