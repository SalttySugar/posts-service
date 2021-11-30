package com.salttysugar.blog.posts.service;

import com.salttysugar.blog.posts.model.Post;
import com.salttysugar.blog.posts.common.ReactiveEntityService;
import com.salttysugar.blog.posts.common.SearchableService;

public interface PostService extends ReactiveEntityService<Post, String>, SearchableService<Post, PostCriteria> {
}
