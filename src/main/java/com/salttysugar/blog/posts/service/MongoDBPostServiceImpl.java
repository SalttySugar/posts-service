package com.salttysugar.blog.posts.service;

import com.salttysugar.blog.posts.model.Post;
import com.salttysugar.blog.posts.persistance.model.MongoPost;
import com.salttysugar.blog.posts.persistance.repository.MongoPostRepository;
import com.salttysugar.blog.posts.common.utils.ConversionUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MongoDBPostServiceImpl implements PostService {
    private final MongoPostRepository repository;
    private final ConversionUtils converter;

    @Override
    public Flux<Post> list() {
        return repository.findAll()
                .map(converter.convert(Post.class));
    }

    @Override
    public Page<Post> list(Pageable pageable) {
        throw new NotImplementedException();
    }

    @Override
    public Mono<Post> retrieve(String id) {
        return repository.findById(id)
                .map(converter.convert(Post.class));
    }

    @Override
    public Mono<Post> create(Post entity) {
        return Mono.just(entity)
                .map(converter.convert(MongoPost.class))
                .flatMap(repository::save)
                .map(converter.convert(Post.class));
    }

    @Override
    public Mono<Post> update(Post entity) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<Post> findAll(PostCriteria criteria) {
        throw new NotImplementedException();
    }

    @Override
    public Page<Post> findAll(Pageable pageable, PostCriteria criteria) {
        throw new NotImplementedException();
    }

    @Override
    public Post findOne(PostCriteria criteria) {
        throw new NotImplementedException();
    }
}
