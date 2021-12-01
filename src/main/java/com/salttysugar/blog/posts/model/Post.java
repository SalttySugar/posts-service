package com.salttysugar.blog.posts.model;

import java.util.Date;
import java.util.Map;

public interface Post {
    String getId();

    void setId(String id);

    String getTitle();

    String getThumbnailId();
    void setThumbnailId(String thumbnailId);

    void setTitle(String title);

    String getSlug();

    void setSlug(String slug);

    String getContent();

    void setContent(String content);

    PostStatus getPostStatus();

    void setPostStatus(PostStatus status);

    Date getCreationTimestamp();

    void setCreationTimestamp(Date timestamp);

    Date getLastUpdateTimestamp();

    void setLastUpdateTimestamp(Date timestamp);

    Map<String, Object> getMeta();

    void setMeta(Map<String, Object> meta);

}
