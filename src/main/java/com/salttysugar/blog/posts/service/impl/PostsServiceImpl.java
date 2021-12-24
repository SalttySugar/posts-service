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
import com.salttysugar.blog.posts.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class PostsServiceImpl implements PostsService {
    private final PostsRepository repository;
    private final ApplicationEventPublisher publisher;

    @Override
    public Mono<Post> findByIdOrSlug(String identifier) {
        return this.repository.findPostByIdOrSlug(identifier, identifier)
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
    public Mono<Post> create(CreatePostDTO postDTO) {
        return Mono.just(postDTO)
                .map(dto -> Post.builder()
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .createdOn(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                        .slug(Optional.of(postDTO.getSlug())
                                .orElseGet(postDTO::getTitle)
                                .trim()
                                .replace(' ', '-')
                                .toLowerCase(Locale.ROOT))
                        .meta(dto.getMeta())
                        .authorId(dto.getAuthorId())
                        .thumbnailId(dto.getThumbnailId())
                        .status(dto.getStatus())
                        .build()
                )
                .flatMap(repository::save)
                .doOnNext(post -> publisher.publishEvent(new PostCreatedEvent(post, this)));
    }

    @Override
    public Mono<Post> update(String identifier, UpdatePostDTO dto) {
        return findByIdOrSlug(identifier)
                .map(post -> {
                    post.setMeta(dto.getMeta());
                    post.setTitle(dto.getTitle());
                    post.setSlug(dto.getSlug());
                    post.setContent(dto.getContent());
                    post.setThumbnailId(dto.getThumbnailId());
                    post.setStatus(dto.getStatus());
                    post.setComments(dto.getComments());
                    post.setUpdatedOn(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                    if (post.getStatus() != PostStatus.PUBLISHED && dto.getStatus() == PostStatus.PUBLISHED) {
                        post.setPublishedOn(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                    }
                    return post;
                })
                .flatMap(repository::save)
                .doOnNext(post -> publisher.publishEvent(new PostUpdatedEvent(post, this)));
    }

    @Override
    public Mono<Post> findById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new PostNotFoundException(id)));
    }

    @Override
    public Mono<Long> count() {
        return repository.count();
    }

    @Override
    public Flux<Post> findAll() {
        return repository.findAll();
    }


    @Override
    public Mono<Post> save(Post post) {
        return Mono.just(post)
                .flatMap(repository::save);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.findById(id)
                .doOnNext(post -> publisher.publishEvent(new PostDeletedEvent(post, this)))
                .flatMap(repository::delete);
    }

}
