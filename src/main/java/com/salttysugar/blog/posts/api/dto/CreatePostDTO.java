package com.salttysugar.blog.posts.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.salttysugar.blog.posts.model.PostStatus;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel("Create post request")
public final class CreatePostDTO {
    @NotEmpty
    String title;
    String content;
    String slug;

    @Builder.Default
    PostStatus status = PostStatus.DRAFT;

    @JsonProperty("author_id")
    @NotEmpty
    String authorId;

    @JsonProperty("thumbnail_id")
    String thumbnailId;

    @Builder.Default
    Map<String, Object> meta = new HashMap<>();
}
