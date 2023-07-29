package com.test.absensi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateException extends GlobalException {
    public DuplicateException(String message) {
        super(message, HttpStatus.CONFLICT.value());
    }
}
