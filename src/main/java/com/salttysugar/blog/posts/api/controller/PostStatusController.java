package com.salttysugar.blog.posts.api.controller;

import com.salttysugar.blog.posts.model.PostStatus;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Post Status")
@RestController
@RequestMapping("api/statuses")
public class PostStatusController {
    @GetMapping
    List<PostStatus> list() {
        return List.of(PostStatus.values());
    }
}
