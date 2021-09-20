package com.patryk.marcisz.gumtreecopy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {
    private String mail;
    private String nick;
}
