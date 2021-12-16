package com.salttysugar.blog.posts.exceptions;

import org.springframework.http.HttpStatus;

public class PostNotFoundException extends HttpException {
    public PostNotFoundException(String identifier) {
        super(String.format("could not find post with identifier: %s", identifier), HttpStatus.NOT_FOUND);
    }
}
