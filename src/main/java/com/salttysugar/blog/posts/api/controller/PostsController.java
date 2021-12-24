package com.salttysugar.blog.posts.api.controller;

import com.salttysugar.blog.posts.api.dto.CreatePostDTO;
import com.salttysugar.blog.posts.api.dto.PostDTO;
import com.salttysugar.blog.posts.api.dto.UpdatePostDTO;
import com.salttysugar.blog.posts.constant.API;
import com.salttysugar.blog.posts.constant.Headers;
import com.salttysugar.blog.posts.model.PostStatus;
import com.salttysugar.blog.posts.service.PostsService;
import com.salttysugar.blog.posts.utils.ApplicationConverter;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Posts")
@RestController
@RequestMapping(API.PATH)
@RequiredArgsConstructor
@Validated
public class PostsController {
    private final PostsService service;
    private final ApplicationConverter converter;

    @GetMapping
    public Mono<ResponseEntity<List<PostDTO>>> list(
            @RequestParam(defaultValue = "10") Long limit,
            @RequestParam(defaultValue = "0") Long skip
    ) {
        return Mono.just(ResponseEntity.ok())
                .flatMap(response -> service.count()
                        .map(count -> response.header(Headers.TOTAL_ITEMS, String.valueOf(count)))
                )
                .flatMap(response -> service.findAll()
                        .skip(skip)
                        .take(limit)
                        .map(converter.convert(PostDTO.class))
                        .collectList()
                        .map(response::body)
                );
    }

    @PostMapping
    public Mono<PostDTO> create(@Valid @RequestBody CreatePostDTO dto) {
        return Mono.just(dto)
                .flatMap(service::create)
                .map(converter.convert(PostDTO.class));
    }

    @GetMapping("/{identifier}")
    public Mono<PostDTO> retrieve(@PathVariable String identifier) {
        return service.findByIdOrSlug(identifier)
                .map(converter.convert(PostDTO.class));
    }


    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return service.deleteById(id);
    }

    @PutMapping("/{id}")
    public Mono<PostDTO> update(@RequestBody UpdatePostDTO dto, @PathVariable String id) {
        return service.update(id, dto)
                .map(converter.convert(PostDTO.class));

    }


    @GetMapping("/statuses")
    public Flux<PostStatus> getStatuses() {
        return Flux.fromArray(PostStatus.values());
    }

}
