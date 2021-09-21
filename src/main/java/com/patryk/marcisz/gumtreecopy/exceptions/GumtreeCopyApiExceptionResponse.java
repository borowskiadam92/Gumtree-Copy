package com.patryk.marcisz.gumtreecopy.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class GumtreeCopyApiExceptionResponse {

    private String message;
    private String errorTime;

    public GumtreeCopyApiExceptionResponse(String message) {
        this.message = message;
        this.errorTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
