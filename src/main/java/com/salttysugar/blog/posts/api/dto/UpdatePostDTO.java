package com.salttysugar.blog.posts.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.salttysugar.blog.posts.model.PostStatus;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.query.Meta;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel("Update post request")
public class UpdatePostDTO {
    String title;
    String slug;
    String content;
    @JsonProperty("thumbnail_id")
    String thumbnailId;
    PostStatus status;
    Map<String, Object> meta = new HashMap<>();

}
