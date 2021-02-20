package com.andrei.accela.codingexercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GeneralException extends RuntimeException {

    public GeneralException(String message) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
}
