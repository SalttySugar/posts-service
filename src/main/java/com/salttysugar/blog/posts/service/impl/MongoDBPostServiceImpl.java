package com.salttysugar.blog.posts.service.impl;

import com.salttysugar.blog.posts.api.dto.RequestPostDTO;
import com.salttysugar.blog.posts.event.PostCreatedEvent;
import com.salttysugar.blog.posts.event.PostDeletedEvent;
import com.salttysugar.blog.posts.event.PostUpdatedEvent;
import com.salttysugar.blog.posts.exception.PostNotFoundException;
import com.salttysugar.blog.posts.model.Post;
import com.salttysugar.blog.posts.model.PostStatus;
import com.salttysugar.blog.posts.persistance.model.MongoPost;
import com.salttysugar.blog.posts.persistance.repository.MongoPostRepository;
import com.salttysugar.blog.posts.service.PostCriteria;
import com.salttysugar.blog.posts.service.PostService;
import com.salttysugar.blog.posts.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MongoDBPostServiceImpl implements PostService {
    private final MongoPostRepository repository;
    private final Validator<RequestPostDTO> validator;
    private final ApplicationEventPublisher publisher;

    protected MongoPost createPostFromDTO(RequestPostDTO dto) {
        validator.validate(dto);
        String slug = dto.getSlug();
        Map<String, Object> meta = dto.getMeta();
        if (slug == null) {
            slug = dto.getTitle().replace(' ', '-')
                    .trim()
                    .toLowerCase(Locale.ROOT);
        }

        if (meta == null) {
            meta = new HashMap<>();
        }

        return MongoPost.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdOn(Date.valueOf(LocalDate.now()))
                .slug(slug)
                .meta(meta)
                .status(PostStatus.PENDING)
                .build();
    }

    @Override
    public Mono<Post> findByIdentifier(String identifier) {
        return this.repository.findAll()
                .filter(post -> post.getSlug().equals(identifier) || post.getId().equals(identifier))
                .next()
                .map(Post.class::cast);
    }

    @Override
    public Mono<Post> create(RequestPostDTO dto) {
        return Mono.just(createPostFromDTO(dto))
                .flatMap(repository::save)
                .map(Post.class::cast)
                .doOnNext(post -> publisher.publishEvent(new PostCreatedEvent(post, this)));
    }

    @Override
    public Mono<Post> update(String identifier, RequestPostDTO dto) {

        return findByIdentifier(identifier)
                .map(post -> {
                    MongoPost payload = createPostFromDTO(dto);
                    post.setMeta(payload.getMeta());
                    post.setTitle(payload.getTitle());
                    post.setSlug(payload.getSlug());
                    post.setContent(payload.getContent());
                    post.setLastUpdateTimestamp(Date.valueOf(LocalDate.now()));
                    return (MongoPost) post;
                })
                .flatMap(repository::save)
                .map(Post.class::cast)
                .doOnNext(post -> publisher.publishEvent(new PostUpdatedEvent(post, this)));
    }

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
        return repository.findById(id)
                .doOnNext(post -> publisher.publishEvent(new PostDeletedEvent(post, this)))
                .flatMap(repository::delete);

    }

}
