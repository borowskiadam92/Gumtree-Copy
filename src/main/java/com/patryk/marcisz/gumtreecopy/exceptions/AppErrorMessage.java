package com.patryk.marcisz.gumtreecopy.exceptions;

import lombok.Getter;

@Getter
public enum AppErrorMessage {

    MISSING_CATEGORY("Category '%s' not found", 404),
    MISSING_AUTHORITY("Authority '%s' not found", 400),
    MISSING_USER("User with username '%s' not found", 512),
    NOT_ALLOWED("You cannot get data", 403),
    USER_EXIST("User with provided email or nick currently exist", 404);

    private String messageTemplate;
    private int status;

    AppErrorMessage(String messageTemplate, int status) {
        this.messageTemplate = messageTemplate;
        this.status = status;
    }



}
