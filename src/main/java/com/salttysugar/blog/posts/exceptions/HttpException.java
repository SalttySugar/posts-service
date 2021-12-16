package com.salttysugar.blog.posts.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public HttpException(String message, HttpStatus status) {
        super(message);

        this.message = message;
        this.status = status;
    }
}
