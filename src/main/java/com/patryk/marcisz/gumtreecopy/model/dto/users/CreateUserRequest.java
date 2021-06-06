package com.patryk.marcisz.gumtreecopy.model.dto.users;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String mail;
    private String password;
    private String username;
    private String nick;

}
