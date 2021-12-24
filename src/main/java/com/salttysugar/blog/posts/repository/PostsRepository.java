package com.salttysugar.blog.posts.repository;

import com.salttysugar.blog.posts.model.Post;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PostsRepository extends ReactiveMongoRepository<Post, String> {
    Mono<Post> findPostBySlugOrId(String slug, String id);
}
