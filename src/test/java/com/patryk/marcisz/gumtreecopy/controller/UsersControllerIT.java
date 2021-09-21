package com.patryk.marcisz.gumtreecopy.controller;

import com.patryk.marcisz.gumtreecopy.TestUtils;
import com.patryk.marcisz.gumtreecopy.model.dto.users.CreateUserRequest;
import com.patryk.marcisz.gumtreecopy.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UsersController.class)
class UsersControllerIT {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Value("classpath:users/create-user-request.json")
    private Resource createUserRequest;

    @Captor
    private ArgumentCaptor<CreateUserRequest> argumentCaptor;

    @Test
    void shouldSaveUser() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.inputStreamtoString(createUserRequest.getInputStream())))
                .andDo(print());

        verify(userService).createUser(argumentCaptor.capture());
        CreateUserRequest value = argumentCaptor.getValue();
        assertEquals("user@user.pl", value.getMail());
        assertEquals("password12345", value.getPassword());
    }

}
