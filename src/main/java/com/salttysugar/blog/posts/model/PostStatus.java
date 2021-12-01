package com.salttysugar.blog.posts.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PostStatus {
    DRAFT("DRAFT"),
    PENDING("PENDING"),
    TRASH("TRASH"),
    FUTURE("FUTURE"),
    PRIVATE("PRIVATE"),
    PUBLISHED("PUBLISHED");

    @Getter
    private final String status;
}
