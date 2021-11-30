package com.salttysugar.blog.posts.converter;

import com.salttysugar.blog.posts.model.Post;
import com.salttysugar.blog.posts.persistance.model.MongoPost;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PostToMongoPostConverter implements Converter<Post, MongoPost> {
    @Override
    public MongoPost convert(Post source) {
        return MongoPost.builder()
                .id(source.getId())
                .title(source.getTitle())
                .content(source.getContent())
                .build();
    }
}
