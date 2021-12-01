package com.salttysugar.blog.posts.validator;

public interface Validator<T> {
    void validate(T target);
}
