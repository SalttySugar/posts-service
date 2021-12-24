package com.salttysugar.blog.posts.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.salttysugar.blog.posts.model.PostStatus;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Post")
public final class PostDTO {
    String id;
    @JsonProperty(required = true)
    String title;
    String content;
    String slug;
    PostStatus status;
    @JsonProperty("thumbnail_id")
    String thumbnailId;
    @JsonProperty("author_id")
    String authorId;
    @JsonProperty("created_on")
    LocalDateTime createdOn;
    @JsonProperty("updated_at")
    LocalDateTime updatedOn;
    @JsonProperty("published_on")
    LocalDateTime publishedOn;
    @Builder.Default
    List<String> comments = new ArrayList<>();
    @Builder.Default
    Map<String, Object> meta = new HashMap<>();
}
