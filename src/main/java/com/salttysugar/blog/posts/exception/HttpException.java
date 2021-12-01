package com.salttysugar.blog.posts.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
