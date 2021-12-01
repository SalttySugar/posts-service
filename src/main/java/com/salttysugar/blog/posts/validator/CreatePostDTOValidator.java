package com.salttysugar.blog.posts.validator;

import com.salttysugar.blog.posts.api.dto.RequestPostDTO;
import org.springframework.stereotype.Component;

@Component
public final class CreatePostDTOValidator implements Validator<RequestPostDTO> {
    @Override
    public void validate(RequestPostDTO target) {
        if (target.getTitle() == null) {
            throw new IllegalArgumentException("title cannot be empty");
        }
    }
}
