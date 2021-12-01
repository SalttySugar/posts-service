package com.salttysugar.blog.posts.service;

import com.salttysugar.blog.posts.api.dto.RequestPostDTO;
import com.salttysugar.blog.posts.model.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {
    Mono<Post> create(RequestPostDTO dto);

    Mono<Post> update(String identifier, RequestPostDTO dto);

    Mono<Post> findById(String id);

    Flux<Post> findAll();

    Flux<Post> findAll(PostCriteria criteria);

    Mono<Post> save(Post post);

    Mono<Void> deleteById(String id);

    Mono<Post> findByIdentifier(String identifier);
}
