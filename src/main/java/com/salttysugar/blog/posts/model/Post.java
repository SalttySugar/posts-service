package com.salttysugar.blog.posts.model;

import java.sql.Date;

public interface Post {
    String getId();
    void setId(String id);

    String getTitle();
    void setTitle(String title);

    String getContent();
    void setContent(String content);
}
