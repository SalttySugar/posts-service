package com.salttysugar.blog.posts.event;

import com.salttysugar.blog.posts.model.Post;

public class PostCreatedEvent extends PostEvent{
    public PostCreatedEvent(Post post, Object source) {
        super(post, source);
    }
}
