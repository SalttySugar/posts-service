package com.salttysugar.blog.posts.service.impl;

import com.salttysugar.blog.posts.model.Post;
import com.salttysugar.blog.posts.persistance.model.MongoPost;
import com.salttysugar.blog.posts.persistance.repository.MongoPostRepository;
import com.salttysugar.blog.posts.service.PostCriteria;
import com.salttysugar.blog.posts.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MongoDBPostServiceImpl implements PostService {
    private final MongoPostRepository repository;

    @Override
    public Mono<Post> findById(String id) {
        return repository.findById(id)
                .map(Post.class::cast);
    }

    @Override
    public Flux<Post> findAll() {
        return repository.findAll()
                .map(Post.class::cast);
    }

    @Override
    public Flux<Post> findAll(PostCriteria criteria) {
        return repository.findAll()
                .map(Post.class::cast);
    }

    @Override
    public Mono<Post> save(Post post) {
        return Mono.just(post)
                .map(MongoPost.class::cast)
                .flatMap(repository::save)
                .map(Post.class::cast);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
