package com.salttysugar.blog.posts.model;

import com.salttysugar.blog.posts.model.Post;
import com.salttysugar.blog.posts.model.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Post {
    @Id
    String id;
    @Indexed(unique = true)
    String title;
    String content;
    @Indexed(unique = true)
    String slug;
    PostStatus status;
    String thumbnailId;
    String authorId;
    LocalDateTime createdOn;
    LocalDateTime updatedOn;
    LocalDateTime publishedOn;
    Map<String, Object> meta;

}
