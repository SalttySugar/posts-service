package com.salttysugar.blog.posts.model;

import java.sql.Date;

public interface Post {
    String getId();

    void setId(String id);

    String getTitle();

    void setTitle(String title);

    String getContent();

    void setContent(String content);

    PostStatus getPostStatus();

    void setPostStatus(PostStatus status);

    Date getCreationTimestamp();

    void setCreationTimestamp(Date timestamp);

    Date getLastUpdateTimestamp();

    void setLastUpdateTimestamp(Date timestamp);


}
