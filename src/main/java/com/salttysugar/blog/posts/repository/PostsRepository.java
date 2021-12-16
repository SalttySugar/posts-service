package com.salttysugar.blog.posts.repository;

import com.salttysugar.blog.posts.model.Post;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends ReactiveMongoRepository<Post, String> {
}
