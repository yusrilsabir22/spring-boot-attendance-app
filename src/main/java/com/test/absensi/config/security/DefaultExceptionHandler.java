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
import java.util.Objects;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        // Example field error
        try {
            String field = Objects.requireNonNull(ex.getFieldError()).getField();
            String rejectedValue = Objects.requireNonNull(ex.getFieldError().getRejectedValue()).toString();
            logger.info("error on field "+field+" with value "+rejectedValue);
        } catch (NullPointerException ignored) {}
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

    @ExceptionHandler({
            AuthenticationException.class,
            UsernameNotFoundException.class,
            JwtException.class,
            RuntimeException.class
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
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler({GlobalException.class})
    public ResponseEntity<Response.Error> globalException(GlobalException ex) {
        Response.Error response = new Response.Error(ex.getStatus(), ex.getMessage());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler({ConversionFailedException.class})
    public ResponseEntity<?> conversionFailed(ConversionFailedException conversionFailedException) {
        System.out.println(conversionFailedException.getValue());
        return ResponseEntity.internalServerError().body("failed");
    }
}
