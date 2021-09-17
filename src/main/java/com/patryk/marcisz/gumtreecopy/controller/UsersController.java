package com.patryk.marcisz.gumtreecopy.controller;

import com.patryk.marcisz.gumtreecopy.model.dto.users.CreateUserRequest;
import com.patryk.marcisz.gumtreecopy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {

    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody CreateUserRequest userRequest){
        userService.createUser(userRequest);
    }

}
