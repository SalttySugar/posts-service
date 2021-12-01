package com.salttysugar.blog.posts.api.advice;

import com.salttysugar.blog.posts.exception.HttpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class HttpExceptionControllerAdvice {
    @ExceptionHandler(value = HttpException.class)
    protected ResponseEntity<Object> handleConflict(HttpException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }
}
