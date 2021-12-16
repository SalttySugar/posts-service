package com.salttysugar.blog.posts;

import com.salttysugar.blog.posts.api.dto.PostDTO;
import com.salttysugar.blog.posts.api.dto.RequestPostDTO;
import com.salttysugar.blog.posts.constant.API;
import com.salttysugar.blog.posts.events.PostCreatedEvent;
import com.salttysugar.blog.posts.events.PostDeletedEvent;
import com.salttysugar.blog.posts.events.PostUpdatedEvent;
import com.salttysugar.blog.posts.exceptions.PostNotFoundException;
import com.salttysugar.blog.posts.model.Post;
import com.salttysugar.blog.posts.model.PostStatus;
import com.salttysugar.blog.posts.service.PostsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureWebTestClient
@RecordApplicationEvents
class PostsApplicationTests extends BaseIntegrationTest {
    @Autowired
    private WebTestClient client;

    @Autowired
    PostsService postsService;

    @Autowired
    ApplicationEvents events;


    @Test
    void shouldCreatePost() {
        var dto = RequestPostDTO.builder()
                .title("test title")
                .content("test content")
                .meta(new HashMap<>() {{
                    put("key_1", "value1");
                    put("key_2", new HashMap<>() {{
                        put("nested_key_1", "nested_value_1");
                    }});
                }})
                .slug("test-title")
                .build();

        client.post()
                .uri(API.PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(dto))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.title").value(equalTo(dto.getTitle()))
                .jsonPath("$.content").value(equalTo(dto.getContent()))
                .jsonPath("$.slug").value(equalTo(dto.getSlug()))
                .jsonPath("$.meta").value(equalTo(dto.getMeta()));

    }


    @Test
    void shouldUpdatePost() {
        var post = postsService.create(RequestPostDTO.builder()
                .title("test post")
                .content("some post content")
                .slug("test-post")
                .build()).block();

        assert post != null;
        var dto = RequestPostDTO.builder()
                .title(post.getTitle())
                .meta(new HashMap<>() {{
                    put("key_1", "value1");
                    put("key_2", new HashMap<>() {{
                        put("nested_key_1", "nested_value_1");
                    }});
                }})
                .build();

        client.put()
                .uri(API.PATH + "/" + post.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(dto))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").value(equalTo(post.getId()))
                .jsonPath("$.title").value(equalTo(dto.getTitle()))
                .jsonPath("$.content").value(equalTo(post.getContent()))
                .jsonPath("$.slug").value(equalTo(post.getSlug()))
                .jsonPath("$.meta").value(equalTo(dto.getMeta()));
    }


    @Test
    void shouldReturnPostByIdOrSlug() {
        var post = postsService.create(RequestPostDTO.builder()
                .title("test post")
                .content("some post content")
                .slug("test-post")
                .build()).block();

        assert post != null;

        client.get()
                .uri(API.PATH + "/" + post.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.title").value(equalTo(post.getTitle()))
                .jsonPath("$.content").value(equalTo(post.getContent()))
                .jsonPath("$.slug").value(equalTo(post.getSlug()))
                .jsonPath("$.meta").value(equalTo(post.getMeta()));


        client.get()
                .uri(API.PATH + "/" + post.getSlug())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.title").value(equalTo(post.getTitle()))
                .jsonPath("$.content").value(equalTo(post.getContent()))
                .jsonPath("$.slug").value(equalTo(post.getSlug()))
                .jsonPath("$.meta").value(equalTo(post.getMeta()));
    }

    @Test
    void shouldReturn404WhenPostDoesNotExist() {
        client.get()
                .uri(API.PATH + "/" + "SURE_DOES_NOT_EXIST")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldReturn400IfDtoDoesNotHaveRequiredFields() {
        var dto = RequestPostDTO.builder()
                .content("this is some content")
                .slug("invalid-post-format")
                .build();
        client.post()
                .uri(API.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(dto))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void shouldDeletePostById() {
        var post = postsService.create(RequestPostDTO.builder()
                .title("test post")
                .content("some post content")
                .slug("test-post")
                .build()).block();

        assert post != null;

        client.delete()
                .uri(API.PATH + "/" + post.getId())
                .exchange()
                .expectStatus().isOk();


        assertThrows(PostNotFoundException.class , () -> postsService.findById(post.getId()));
    }


    @Test
    void shouldDeletePostBySlug() throws InterruptedException {
        var post = postsService.create(RequestPostDTO.builder()
                .title("test post")
                .content("some post content")
                .slug("test-post")
                .build()).block();

        assert post != null;

        client.delete()
                .uri(API.PATH + "/" + post.getSlug())
                .exchange()
                .expectStatus().isOk()
                .returnResult(Void.class)
                .getResponseBody()
                .blockFirst();

        assertThrows(PostNotFoundException.class , () -> postsService.findById(post.getId()));

    }


    @Test
    void shouldReturnCollectionOfPostsWithPaginationInfo() {
        List<Post> posts = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> RequestPostDTO.builder()
                        .title("post title #" + i)
                        .content("post content #" + i)
                        .build())
                .map(postsService::create)
                .map(Mono::block)
                .collect(Collectors.toList());

        client.get()
                .uri(API.PATH)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().value("x-total-records", equalTo(posts.size()))
                .expectHeader().value("x-total-pages", equalTo(1))
                .expectBody()
                .jsonPath("$").isArray();
    }


    @Test
    void postShouldBeCreatedWithDraftStatus() {
        var dto = RequestPostDTO.builder()
                .title("test title")
                .content("test content")
                .meta(new HashMap<>() {{
                    put("key_1", "value1");
                    put("key_2", new HashMap<>() {{
                        put("nested_key_1", "nested_value_1");
                    }});
                }})
                .slug("test-title")
                .build();

        client.post()
                .uri(API.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(dto))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("status").value(equalTo(PostStatus.DRAFT));
    }


    @Test
    void shouldFireEventWhenPostCreated() {
        var dto = RequestPostDTO.builder()
                .title("test title")
                .content("test content")
                .meta(new HashMap<>() {{
                    put("key_1", "value1");
                    put("key_2", new HashMap<>() {{
                        put("nested_key_1", "nested_value_1");
                    }});
                }})
                .slug("test-title")
                .build();

        client.post()
                .uri(API.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(dto))
                .exchange()
                .expectStatus().isOk()
                .returnResult(PostDTO.class)
                .getResponseBody()
                .collectList()
                .block();

        assertEquals(1, events.stream(PostCreatedEvent.class)
                .filter(e -> e.getPost().getTitle().equals(dto.getTitle()))
                .count());
    }


    @Test
    void shouldFireEventWhenPostDeleted() {
        var post = postsService.create(RequestPostDTO.builder()
                .title("test post")
                .content("some post content")
                .slug("test-post")
                .build()).block();

        assert post != null;
        client.delete()
                .uri(API.PATH + "/" + post.getId())
                .exchange()
                .expectStatus().isOk()
                .returnResult(PostDTO.class)
                .getResponseBody()
                .blockFirst();

        assertEquals(1, events.stream(PostDeletedEvent.class)
                .filter(e -> e.getPost().getTitle().equals(post.getTitle()))
                .count());
    }

    @Test
    void shouldFireEventWhenPostUpdated() {
        var post = postsService.create(RequestPostDTO.builder()
                .title("test post")
                .content("some post content")
                .slug("test-post")
                .build()).block();
        assert post != null;

        var dto = RequestPostDTO.builder()
                .title("updated_post_title")
                .build();

        client.put()
                .uri(API.PATH + "/" + post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(dto))
                .exchange()
                .expectStatus().isOk()
                .returnResult(PostDTO.class)
                .getResponseBody()
                .blockFirst();

        assertEquals(1, events.stream(PostUpdatedEvent.class)
                .filter(e -> e.getPost().getTitle().equals(post.getTitle()))
                .count());
    }

}
