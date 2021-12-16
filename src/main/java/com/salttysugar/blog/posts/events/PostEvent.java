package com.salttysugar.blog.posts.events;

import com.salttysugar.blog.posts.model.Post;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public abstract class PostEvent extends ApplicationEvent {
    private final Post post;
    private final Object source;

    public PostEvent(Post post, Object source) {
        super(source);
        this.post = post;
        this.source = source;
    }
}
