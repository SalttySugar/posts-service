package com.salttysugar.blog.posts.converter;

import com.salttysugar.blog.posts.api.dto.ResponsePostDTO;
import com.salttysugar.blog.posts.model.Post;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public final class PostToPostDTOConverter implements Converter<Post, ResponsePostDTO> {
    @Override
    public ResponsePostDTO convert(Post source) {
        return ResponsePostDTO.builder()
                .id(source.getId())
                .content(source.getContent())
                .title(source.getTitle())
                .slug(source.getSlug())
                .createdOn(source.getCreationTimestamp())
                .updatedOn(source.getLastUpdateTimestamp())
                .meta(source.getMeta())
                .status(source.getPostStatus())
                .build();
    }
}
