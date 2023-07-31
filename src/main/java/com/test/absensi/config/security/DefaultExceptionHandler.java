package com.test.absensi.config.security;

import com.test.absensi.exceptions.DuplicateException;
import com.test.absensi.exceptions.GlobalException;
import com.test.absensi.models.Response;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.MissingResourceException;
import java.util.Objects;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            AuthenticationException.class,
            UsernameNotFoundException.class,
            JwtException.class
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

//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<?> missingResourceEx(Exception ex) {
//        System.out.println(ex.getMessage());
//        System.out.println(ex.);
//        return ResponseEntity.internalServerError().body(ex.getMessage());
//    }
}
