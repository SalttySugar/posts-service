package com.salttysugar.blog.posts.event;

import com.salttysugar.blog.posts.model.Post;

public class PostDeletedEvent extends PostEvent{

    public PostDeletedEvent(Post post, Object source) {
        super(post, source);
    }
}
