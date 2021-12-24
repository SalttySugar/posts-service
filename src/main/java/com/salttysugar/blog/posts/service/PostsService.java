package com.salttysugar.blog.posts.service;

import com.salttysugar.blog.posts.api.dto.CreatePostDTO;
import com.salttysugar.blog.posts.api.dto.UpdatePostDTO;
import com.salttysugar.blog.posts.exceptions.PostNotFoundException;
import com.salttysugar.blog.posts.model.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostsService {
    Mono<Post> create(CreatePostDTO dto);

    Mono<Post> update(String identifier, UpdatePostDTO dto);

    Mono<Post> findById(String id);

    Mono<Long> count();

    Flux<Post> findAll();


    Mono<Post> save(Post post);

    Mono<Void> deleteById(String id);

    Mono<Post> findByIdOrSlug(String identifier);

    Mono<Boolean> existsById(String id);

    Mono<Void> deleteAll();
}
