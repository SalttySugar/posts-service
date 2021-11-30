package com.salttysugar.blog.posts.api.dto;

import com.salttysugar.blog.posts.model.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class PostDTO {
    String id;
    String title;
    String content;
    PostStatus status;
    Date createdOn;
    Date updatedOn;
}
