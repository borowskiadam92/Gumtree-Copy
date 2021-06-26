package com.patryk.marcisz.gumtreecopy.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(GumtreeCopyApiException.class)
    public ResponseEntity<GumtreeCopyApiExceptionResponse> handleException(GumtreeCopyApiException e){
        return ResponseEntity.status(e.getResponseStatus()).body(new GumtreeCopyApiExceptionResponse(e.getMessage()));
    }

}
