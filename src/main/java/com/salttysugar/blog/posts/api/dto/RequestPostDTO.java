package com.salttysugar.blog.posts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public final class RequestPostDTO {
    String title;
    String content;
    String slug;
    Map<String, Object> meta;
}
