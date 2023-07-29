package com.test.absensi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GlobalException extends RuntimeException {
    private String message;
    private int status;
}
