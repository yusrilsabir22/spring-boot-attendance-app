package com.test.absensi.config.security;

import com.test.absensi.exceptions.GlobalException;
import com.test.absensi.models.Response;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;


@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            AuthenticationException.class,
            UsernameNotFoundException.class,
            AccessDeniedException.class,
            JwtException.class,
            InsufficientAuthenticationException.class
    })
    public ResponseEntity<Response.Error> handleAuthenticationException(Exception ex) {
        final String message;
        if(ex instanceof UsernameNotFoundException) {
            message = ex.getMessage();
        } else {
            message = "Unauthorized";
        }
        Response.Error response = new Response.Error(HttpStatus.UNAUTHORIZED.value(),
                message);
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
    }

    @ExceptionHandler({GlobalException.class})
    public ResponseEntity<Response.Error> globalException(Exception ex) {
        Response.Error response = new Response.Error(HttpStatus.NOT_IMPLEMENTED.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> exceptionError(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex);
    }
}
