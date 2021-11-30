package com.salttysugar.blog.posts.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveEntityService<T, ID> {
    Flux<T> list();

    Page<T> list(Pageable pageable);

    Mono<T> retrieve(ID id);

    Mono<T> create(T entity);

    Mono<T> update(T entity);

    void delete(ID id);

    void deleteAll();
}

