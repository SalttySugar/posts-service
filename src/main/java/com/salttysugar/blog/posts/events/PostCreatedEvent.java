package com.salttysugar.blog.posts.events;

import com.salttysugar.blog.posts.model.Post;

public class PostCreatedEvent extends PostEvent {
    public PostCreatedEvent(Post post, Object source) {
        super(post, source);
    }
}
