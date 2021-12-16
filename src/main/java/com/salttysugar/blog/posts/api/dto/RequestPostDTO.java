package com.salttysugar.blog.posts.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public final class RequestPostDTO {
    @NotBlank
    @NotNull
    String title;
    String content;
    String slug;

    @Builder.Default
    Map<String, Object> meta = new HashMap<>();
}
