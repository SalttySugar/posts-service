package com.salttysugar.blog.posts.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public final class RequestPostDTO {
    @JsonProperty(required = true)
    String title;
    String content;
    String slug;

    @Builder.Default
    Map<String, Object> meta = new HashMap<>();
}
