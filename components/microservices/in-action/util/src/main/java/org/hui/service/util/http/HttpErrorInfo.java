package org.hui.service.util.http;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record HttpErrorInfo(
        ZonedDateTime timestamp,
        String path,
        HttpStatus httpStatus,
        String message) {
    HttpErrorInfo(HttpStatus httpStatus,String path, String message) {
        this(ZonedDateTime.now(),path,httpStatus,message);
    }
}
