package com.salttysugar.blog.posts.persistance.model;

import com.salttysugar.blog.posts.model.Post;
import com.salttysugar.blog.posts.model.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MongoPost implements Post {
    @Id
    String id;
    @Indexed(unique = true)
    String title;
    String content;
    @Indexed(unique = true)
    String slug;
    PostStatus status;
    Date createdOn;
    Date updatedOn;
    Date publishedOn;
    Map<String, Object> meta;

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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
    public java.util.Date getLastUpdateTimestamp() {
        return updatedOn;
    }

    @Override
    public void setLastUpdateTimestamp(Date timestamp) {
        updatedOn = timestamp;
    }

    @Override
    public Map<String, Object> getMeta() {
        return meta;
    }

    @Override
    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }

}
