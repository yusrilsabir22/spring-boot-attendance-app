package com.test.absensi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class GlobalException extends RuntimeException {
    private String message;
    private int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    public GlobalException(String msg) {
        super(msg);
    }
}
