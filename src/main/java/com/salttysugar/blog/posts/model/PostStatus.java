package com.salttysugar.blog.posts.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PostStatus {
    DRAFT("DRAFT"),
    PUBLISHED("PUBLISHED");

    @Getter
    private final String status;
}
