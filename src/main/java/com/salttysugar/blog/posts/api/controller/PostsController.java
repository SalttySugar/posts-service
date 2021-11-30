package com.salttysugar.blog.posts.api.controller;

import com.salttysugar.blog.posts.api.dto.PostDTO;
import com.salttysugar.blog.posts.model.Post;
import com.salttysugar.blog.posts.service.PostService;
import com.salttysugar.blog.posts.common.constant.API;
import com.salttysugar.blog.posts.common.utils.ConversionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping(value = API.V1.POST.BASE_URL)
@RequiredArgsConstructor
public class PostsController {
    private final PostService service;
    private final ConversionUtils converter;

    @GetMapping
    public Flux<PostDTO> list() {
        return service.list()
                .map(converter.convert(PostDTO.class));
    }


    @PostMapping
    public Mono<PostDTO> create(@RequestBody PostDTO dto) {
        return Mono.just(dto)
                .map(converter.convert(Post.class))
                .flatMap(service::create)
                .map(converter.convert(PostDTO.class));
    }

    @GetMapping("/{id}")
    public Mono<PostDTO> retrieve(@PathVariable String id) {
        return service.retrieve(id)
                .map(converter.convert(PostDTO.class));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public PostDTO update(@RequestBody PostDTO dto, @PathVariable String id) {
        dto.setId(id);
        return Optional.of(dto)
                .map(converter.convert(Post.class))
                .map(service::update)
                .map(converter.convert(PostDTO.class))
                .orElseThrow(RuntimeException::new);
    }

}
