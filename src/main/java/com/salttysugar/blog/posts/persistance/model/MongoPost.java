package com.salttysugar.blog.posts.persistance.model;

import com.salttysugar.blog.posts.model.Post;
import com.salttysugar.blog.posts.model.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MongoPost implements Post  {
    @Id
    String id;
    String title;
    String content;
    @Builder.Default
    PostStatus status = PostStatus.PUBLISHED;
    @Builder.Default
    Date createdOn = Date.valueOf(LocalDate.now());
    Date updatedOn;
    Date publishedOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public PostStatus getPostStatus() {
        return status;
    }

    @Override
    public void setPostStatus(PostStatus status) {
        this.status = status;
    }

    @Override
    public Date getCreationTimestamp() {
        return createdOn;
    }

    @Override
    public void setCreationTimestamp(Date timestamp) {
        createdOn = timestamp;
    }

    @Override
    public Date getLastUpdateTimestamp() {
        return updatedOn;
    }

    @Override
    public void setLastUpdateTimestamp(Date timestamp) {
        updatedOn = timestamp;
    }

}
