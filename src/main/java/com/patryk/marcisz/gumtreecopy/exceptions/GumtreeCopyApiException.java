package com.patryk.marcisz.gumtreecopy.exceptions;

import lombok.Getter;

@Getter
public class GumtreeCopyApiException extends RuntimeException {

    private int responseStatus;
    //String detailedMessage, niejawny wynika z dziedziczenia

    public GumtreeCopyApiException(AppErrorMessage message, String ... params){
        super(String.format(message.getMessageTemplate(), params));
        this.responseStatus = message.getStatus();
    }
}
