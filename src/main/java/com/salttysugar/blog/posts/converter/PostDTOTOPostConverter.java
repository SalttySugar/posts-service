package com.salttysugar.blog.posts.converter;

import com.salttysugar.blog.posts.api.dto.ResponsePostDTO;
import com.salttysugar.blog.posts.model.Post;
import com.salttysugar.blog.posts.persistance.model.MongoPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Builder
@AllArgsConstructor
public final class PostDTOTOPostConverter implements Converter<ResponsePostDTO, Post> {
    @Override
    public Post convert(ResponsePostDTO source) {
        return MongoPost.builder()
                .id(source.getId())
                .content(source.getContent())
                .title(source.getTitle())
                .createdOn(source.getCreatedOn())
                .updatedOn(source.getCreatedOn())
                .status(source.getStatus())
                .slug(source.getSlug())
                .build();
    }
}
