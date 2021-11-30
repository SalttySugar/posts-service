package com.salttysugar.blog.posts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class PostImpl implements Post {
    String id;
    String title;
    String content;
}
