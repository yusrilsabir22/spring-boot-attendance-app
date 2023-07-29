package com.test.absensi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequest extends GlobalException {

    public BadRequest(String message) {
        super(message, HttpStatus.BAD_REQUEST.value());
    }
}
