package com.salttysugar.blog.posts.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EntityService<T, ID> {
    List<T> list();
    Page<T> list(Pageable pageable);
    T retrieve(ID id);
    T create(T entity);
    T update(T entity);
    void delete(ID id);
    void deleteAll();
}
