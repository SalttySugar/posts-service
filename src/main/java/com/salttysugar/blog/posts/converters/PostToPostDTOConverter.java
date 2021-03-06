package com.salttysugar.blog.posts.converters;

import com.salttysugar.blog.posts.api.dto.PostDTO;
import com.salttysugar.blog.posts.model.Post;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public final class PostToPostDTOConverter implements Converter<Post, PostDTO> {
    @Override
    public PostDTO convert(Post source) {
        return PostDTO.builder()
                .id(source.getId())
                .content(source.getContent())
                .title(source.getTitle())
                .slug(source.getSlug())
                .createdOn(source.getCreatedOn())
                .updatedOn(source.getUpdatedOn())
                .publishedOn(source.getPublishedOn())
                .meta(source.getMeta())
                .status(source.getStatus())
                .thumbnailId(source.getThumbnailId())
                .authorId(source.getAuthorId())
                .comments(source.getComments())
                .build();
    }
}
