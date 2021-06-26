package com.patryk.marcisz.gumtreecopy.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Objects;

@Component
@RequestScope
public class GumtreeCopyUserContext {

    private String username;

    public String getCurrentUserName() {
        if (Objects.isNull(username)) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return username;
    }
}
