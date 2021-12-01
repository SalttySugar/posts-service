package com.salttysugar.blog.posts.api.dto;

import com.salttysugar.blog.posts.model.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class ResponsePostDTO {
    String id;
    String title;
    String content;
    String slug;
    PostStatus status;
    Date createdOn;
    Date updatedOn;
    Map<String, Object> meta;
}
