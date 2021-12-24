package com.salttysugar.blog.posts.service.impl;

import com.salttysugar.blog.posts.api.dto.CreatePostDTO;
import com.salttysugar.blog.posts.api.dto.UpdatePostDTO;
import com.salttysugar.blog.posts.events.PostCreatedEvent;
import com.salttysugar.blog.posts.events.PostDeletedEvent;
import com.salttysugar.blog.posts.events.PostUpdatedEvent;
import com.salttysugar.blog.posts.exceptions.PostNotFoundException;
import com.salttysugar.blog.posts.model.Post;
import com.salttysugar.blog.posts.model.PostStatus;
import com.salttysugar.blog.posts.repository.PostsRepository;
import com.salttysugar.blog.posts.service.PostCriteria;
import com.salttysugar.blog.posts.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class PostsServiceImpl implements PostsService {
    private final PostsRepository repository;
    private final ApplicationEventPublisher publisher;

    protected Post createPostFromDTO(CreatePostDTO dto) {
        String slug = dto.getSlug();
        Map<String, Object> meta = dto.getMeta();

        if (slug == null) {
            slug = dto.getTitle().replace(' ', '-')
                    .trim()
                    .toLowerCase(Locale.ROOT);
        }


        return Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdOn(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .slug(slug)
                .meta(meta)
                .authorId(dto.getAuthorId())
                .thumbnailId(dto.getThumbnailId())
                .status(dto.getStatus())
                .build();
    }

    @Override
    public Mono<Post> findByIdentifier(String identifier) {
        return this.repository.findPostBySlugOrId(identifier, identifier)
                .switchIfEmpty(Mono.error(new PostNotFoundException(identifier)));
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public Mono<Void> deleteAll() {
        return repository.deleteAll();
    }

    @Override
    public Mono<Post> create(CreatePostDTO dto) {
        return Mono.just(createPostFromDTO(dto))
                .flatMap(repository::save)
                .map(Post.class::cast)
                .doOnNext(post -> publisher.publishEvent(new PostCreatedEvent(post, this)));
    }

    @Override
    public Mono<Post> update(String identifier, UpdatePostDTO dto) {
        return findByIdentifier(identifier)
                .map(post -> {
                    post.setMeta(dto.getMeta());
                    post.setTitle(dto.getTitle());
                    post.setSlug(dto.getSlug());
                    post.setContent(dto.getContent());
                    post.setThumbnailId(dto.getThumbnailId());
                    post.setStatus(dto.getStatus());
                    post.setUpdatedOn(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                    if (post.getStatus() != PostStatus.PUBLISHED && dto.getStatus() == PostStatus.PUBLISHED) {
                        post.setPublishedOn(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                    }
                    return post;
                })
                .flatMap(repository::save)
                .map(Post.class::cast)
                .doOnNext(post -> publisher.publishEvent(new PostUpdatedEvent(post, this)));
    }

    @Override
    public Mono<Post> findById(String id) {
        return repository.findById(id)
                .map(Post.class::cast)
                .switchIfEmpty(Mono.error(new PostNotFoundException(id)));
    }

    @Override
    public Mono<Long> count() {
        return repository.count();
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
                .map(Post.class::cast)
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
