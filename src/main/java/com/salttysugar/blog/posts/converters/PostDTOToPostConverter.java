package com.salttysugar.blog.posts.converters;

import com.salttysugar.blog.posts.api.dto.PostDTO;
import com.salttysugar.blog.posts.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Builder
@AllArgsConstructor
public final class PostDTOToPostConverter implements Converter<PostDTO, Post> {
    @Override
    public Post convert(PostDTO source) {
        return Post.builder()
                .id(source.getId())
                .content(source.getContent())
                .title(source.getTitle())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getCreatedAt())
                .status(source.getStatus())
                .slug(source.getSlug())
                .build();
    }
}
