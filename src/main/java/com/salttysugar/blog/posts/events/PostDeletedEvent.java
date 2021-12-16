package com.salttysugar.blog.posts.events;

import com.salttysugar.blog.posts.model.Post;

public class PostDeletedEvent extends PostEvent {

    public PostDeletedEvent(Post post, Object source) {
        super(post, source);
    }
}
