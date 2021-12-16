package com.salttysugar.blog.posts.events;

import com.salttysugar.blog.posts.model.Post;

public class PostUpdatedEvent extends PostEvent {
    public PostUpdatedEvent(Post post, Object source) {
        super(post, source);
    }
}
