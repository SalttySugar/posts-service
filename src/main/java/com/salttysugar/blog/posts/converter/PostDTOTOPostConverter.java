package com.salttysugar.blog.posts.converter;

import com.salttysugar.blog.posts.api.dto.PostDTO;
import com.salttysugar.blog.posts.model.Post;
import com.salttysugar.blog.posts.model.PostImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Builder
@AllArgsConstructor
public final class PostDTOTOPostConverter implements Converter<PostDTO, Post> {
    @Override
    public Post convert(PostDTO source) {
        return PostImpl.builder()
                .id(source.getId())
                .content(source.getContent())
                .title(source.getTitle())
                .build();
    }
}
