package com.salttysugar.blog.posts.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.salttysugar.blog.posts.model.PostStatus;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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
    @JsonProperty("thumbnail_id")
    String thumbnailId;
    PostStatus status;
    @JsonProperty("created_at")
    Date createdAt;
    @JsonProperty("updated_at")
    Date updatedAt;
    @Builder.Default
    Map<String, Object> meta = new HashMap<>();
}
