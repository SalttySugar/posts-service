package com.salttysugar.blog.posts.api.controller;

import com.salttysugar.blog.posts.api.dto.PostDTO;
import com.salttysugar.blog.posts.model.Post;
import com.salttysugar.blog.posts.service.PostService;
import com.salttysugar.blog.posts.utils.ConversionUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Api(tags = "Posts")
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class PostsController {
    private final PostService service;
    private final ConversionUtils converter;

    @GetMapping
    public Flux<PostDTO> list() {
        return service.findAll()
                .map(converter.convert(PostDTO.class));
    }


    @PostMapping
    public Mono<PostDTO> create(@RequestBody PostDTO dto) {
        return Mono.just(dto)
                .map(converter.convert(Post.class))
                .flatMap(service::save)
                .map(converter.convert(PostDTO.class));
    }

    @GetMapping("/{id}")
    public Mono<PostDTO> retrieve(@PathVariable String id) {
        return service.findById(id)
                .map(converter.convert(PostDTO.class));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return service.deleteById(id);
    }

    @PutMapping("/{id}")
    public Mono<PostDTO> update(@RequestBody PostDTO dto, @PathVariable String id) {
        dto.setId(id);
        return Mono.just(dto)
                .map(converter.convert(Post.class))
                .flatMap(service::save)
                .map(converter.convert(PostDTO.class));
    }

}
