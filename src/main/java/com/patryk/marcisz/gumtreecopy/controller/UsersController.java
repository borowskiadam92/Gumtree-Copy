package com.patryk.marcisz.gumtreecopy.controller;

import com.patryk.marcisz.gumtreecopy.model.UserResponse;
import com.patryk.marcisz.gumtreecopy.model.dto.users.CreateUserRequest;
import com.patryk.marcisz.gumtreecopy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {

    private final UserService userService;

    @PostMapping("/users")
    public void createUser(@RequestBody CreateUserRequest userRequest){
        userService.createUser(userRequest);
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/current-user")
    public UserResponse getCurrentUser(Principal principal){
        return userService.getUser(principal.getName());
    }

    @GetMapping("/activate-user/{confirmation-token}")
    public void activateUser(@PathVariable("confirmation-token") String confirmationToken){
        userService.activateUser(confirmationToken);
    }

}
