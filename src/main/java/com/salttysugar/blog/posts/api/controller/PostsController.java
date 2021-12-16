package com.salttysugar.blog.posts.api.controller;

import com.salttysugar.blog.posts.api.dto.RequestPostDTO;
import com.salttysugar.blog.posts.api.dto.PostDTO;
import com.salttysugar.blog.posts.constant.API;
import com.salttysugar.blog.posts.model.PostStatus;
import com.salttysugar.blog.posts.service.PostsService;
import com.salttysugar.blog.posts.utils.ConversionUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Api(tags = "Posts")
@RestController
@RequestMapping(API.PATH)
@RequiredArgsConstructor
public class PostsController {
    private final PostsService service;
    private final ConversionUtils converter;

    @GetMapping
    public Flux<PostDTO> list() {
        return service.findAll()
                .map(converter.convert(PostDTO.class));
    }

    @PostMapping
    public Mono<PostDTO> create(@RequestBody RequestPostDTO dto) {
        return Mono.just(dto)
                .flatMap(service::create)
                .map(converter.convert(PostDTO.class));
    }

    @GetMapping("/{identifier}")
    public Mono<PostDTO> retrieve(@PathVariable String identifier) {
        return service.findByIdentifier(identifier)
                .map(converter.convert(PostDTO.class));
    }


    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return service.deleteById(id);
    }

    @PutMapping("/{id}")
    public Mono<PostDTO> update(@RequestBody RequestPostDTO dto, @PathVariable String id) {
        return service.update(id, dto)
                .map(converter.convert(PostDTO.class));

    }


    @GetMapping("/statuses")
    public Flux<PostStatus> getStatuses() {
        return Flux.fromArray(PostStatus.values());
    }

}
