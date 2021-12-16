package com.salttysugar.blog.posts.service;

import com.salttysugar.blog.posts.model.PostStatus;
import org.springframework.lang.Nullable;

public class PostCriteria {
    @Nullable
    String title;
    @Nullable
    PostStatus status;
}
