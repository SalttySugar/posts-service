package com.salttysugar.blog.posts.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchableService<T,C> {
    List<T> findAll(C criteria);
    Page<T> findAll(Pageable pageable, C criteria);
    T findOne(C criteria);
}
