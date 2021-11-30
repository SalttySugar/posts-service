package com.salttysugar.blog.posts.converter;

import com.salttysugar.blog.posts.model.Post;
import com.salttysugar.blog.posts.model.PostImpl;
import com.salttysugar.blog.posts.persistance.model.MongoPost;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MongoPostToPostConverter implements Converter<MongoPost, Post> {

    @Override
    public Post convert(MongoPost source) {
        return PostImpl.builder()
                .id(source.getId())
                .title(source.getTitle())
                .content(source.getContent())
                .build();
    }
}
