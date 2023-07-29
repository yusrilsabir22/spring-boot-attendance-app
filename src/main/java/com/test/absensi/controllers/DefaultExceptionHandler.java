package com.test.absensi.controllers;

import com.test.absensi.exceptions.DuplicateException;
import com.test.absensi.exceptions.GlobalException;
import com.test.absensi.models.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Response.Error> handleAuthenticationException(Exception ex) {
        final String message;
        if(ex instanceof UsernameNotFoundException) {
            message = ex.getMessage();
        } else {
            message = "Authentication failed at controller advice";
        }
        Response.Error response = new Response.Error(HttpStatus.UNAUTHORIZED.value(),
                message);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler({DuplicateException.class})
    public ResponseEntity<Response.Error> handleDuplicateException(Exception ex) {

        Response.Error response = new Response.Error(HttpStatus.CONFLICT.value(),
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler({GlobalException.class})
    public ResponseEntity<Response.Error> globalException(GlobalException ex) {
        Response.Error response = new Response.Error(ex.getStatus(), ex.getMessage());
        return ResponseEntity.status(response.getErrorCode()).body(response);
    }
}
