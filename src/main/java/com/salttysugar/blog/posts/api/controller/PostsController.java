package com.salttysugar.blog.posts.api.controller;

import com.salttysugar.blog.posts.api.dto.RequestPostDTO;
import com.salttysugar.blog.posts.api.dto.ResponsePostDTO;
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
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostsController {
    private final PostService service;
    private final ConversionUtils converter;

    @GetMapping
    public Flux<ResponsePostDTO> list() {
        return service.findAll()
                .map(converter.convert(ResponsePostDTO.class));
    }

    @PostMapping
    public Mono<ResponsePostDTO> create(@RequestBody RequestPostDTO dto) {
        return Mono.just(dto)
                .flatMap(service::create)
                .map(converter.convert(ResponsePostDTO.class));
    }

    @GetMapping("/{identifier}")
    public Mono<ResponsePostDTO> retrieve(@PathVariable String identifier) {
        return service.findByIdentifier(identifier)
                .map(converter.convert(ResponsePostDTO.class));
    }


    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return service.deleteById(id);
    }

    @PutMapping("/{id}")
    public Mono<ResponsePostDTO> update(@RequestBody RequestPostDTO dto, @PathVariable String id) {
        return service.update(id, dto)
                .map(converter.convert(ResponsePostDTO.class));

    }

}
