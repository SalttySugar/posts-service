package com.salttysugar.blog.posts;

import com.salttysugar.blog.posts.api.dto.CreatePostDTO;
import com.salttysugar.blog.posts.api.dto.PostDTO;
import com.salttysugar.blog.posts.api.dto.UpdatePostDTO;
import com.salttysugar.blog.posts.constant.API;
import com.salttysugar.blog.posts.constant.Headers;
import com.salttysugar.blog.posts.model.Post;
import com.salttysugar.blog.posts.model.PostStatus;
import com.salttysugar.blog.posts.service.PostsService;
import com.salttysugar.blog.posts.utils.ApplicationConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest
@AutoConfigureWebTestClient
@RecordApplicationEvents
class PostsApplicationTests extends BaseIntegrationTest {
    @Autowired
    private WebTestClient client;

    @Autowired
    ApplicationConverter converter;
    @Autowired
    PostsService postsService;

    Long total_items;

    @BeforeEach
    void setUp() {
        for (int i = 1; i <= 50; i++) {
            if (i <= 5) {
                postsService.save(Post.builder()
                        .title(String.format("test post #%d", i))
                        .content("lorem ipsum dolor sit amet")
                        .authorId("1")
                        .createdOn(LocalDateTime.now().minusWeeks(5))
                        .publishedOn(LocalDateTime.now().minusWeeks(4))
                        .status(PostStatus.PUBLISHED)
                        .build()
                ).block();
            }


            if (i > 5 && i <= 10) {
                postsService.save(Post.builder()
                        .title(String.format("test post #%d", i))
                        .content("lorem ipsum dolor sit amet")
                        .authorId("2")
                        .createdOn(LocalDateTime.now().minusWeeks(5))
                        .status(PostStatus.PENDING)
                        .build()
                ).block();
            }

            if (i > 10 && i <= 15) {
                postsService.save(Post.builder()
                        .title(String.format("test post #%d", i))
                        .content("lorem ipsum dolor sit amet")
                        .authorId("3")
                        .createdOn(LocalDateTime.now().minusWeeks(4))
                        .publishedOn(LocalDateTime.now().minusWeeks(4))
                        .status(PostStatus.PUBLISHED)
                        .build()
                ).block();
            }

            if (i > 15 && i <= 20) {
                postsService.save(Post.builder()
                        .title(String.format("test post #%d", i))
                        .content("lorem ipsum dolor sit amet")
                        .authorId("3")
                        .createdOn(LocalDateTime.now().minusWeeks(4))
                        .status(PostStatus.PENDING)
                        .build()
                ).block();
            }

            if (i > 20 && i <= 25) {
                postsService.save(Post.builder()
                        .title(String.format("test post #%d", i))
                        .content("lorem ipsum dolor sit amet")
                        .authorId("1")
                        .createdOn(LocalDateTime.now().minusWeeks(3))
                        .status(PostStatus.TRASH)
                        .build()
                ).block();
            }


            if (i > 25 && i <= 30) {
                postsService.save(Post.builder()
                        .title(String.format("test post #%d", i))
                        .content("lorem ipsum dolor sit amet")
                        .authorId("1")
                        .createdOn(LocalDateTime.now().minusWeeks(3))
                        .updatedOn(LocalDateTime.now().minusWeeks(1))
                        .status(PostStatus.PRIVATE)
                        .build()
                ).block();
            }


            if (i > 30 && i <= 40) {
                postsService.save(Post.builder()
                        .title(String.format("test post #%d", i))
                        .content("lorem ipsum dolor sit amet")
                        .authorId("4")
                        .createdOn(LocalDateTime.now().minusWeeks(3))
                        .updatedOn(LocalDateTime.now().minusWeeks(1))
                        .status(PostStatus.PENDING)
                        .build()
                ).block();
            }

            if (i > 40 && i < 50) {
                postsService.save(Post.builder()
                        .title(String.format("test post #%d", i))
                        .content("lorem ipsum dolor sit amet")
                        .authorId("1")
                        .createdOn(LocalDateTime.now().minusWeeks(3))
                        .updatedOn(LocalDateTime.now().minusWeeks(1))
                        .status(PostStatus.DRAFT)
                        .build()
                ).block();
            }

            total_items = postsService.count().block();
        }
    }

    @AfterEach
    void tearDown() {
        postsService.deleteAll().block();
    }


    @Nested
    public class PostsApplicationCrudTests {
        @Test
        void shouldCreatePost() {
            var dto = CreatePostDTO.builder()
                    .title("test title")
                    .content("test content")
                    .authorId("1")
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
                    .jsonPath("$.title").value(is(dto.getTitle()))
                    .jsonPath("$.content").value(is(dto.getContent()))
                    .jsonPath("$.slug").value(is(dto.getSlug()))
                    .jsonPath("$.author_id").value(is(dto.getAuthorId()))
                    .jsonPath("$.thumbnail_id").value(is(dto.getThumbnailId()))
                    .jsonPath("$.status").value(is(dto.getStatus().toString()))
                    .jsonPath("$.comments").exists()
                    .jsonPath("$.meta").value(is(dto.getMeta()));

        }

        @Test
        void shouldUpdatePost() {
            var post = postsService.findAll()
                    .take(1)
                    .next()
                    .block();

            assert post != null;

            var dto = UpdatePostDTO.builder()
                    .title("some new title")
                    .content("updated_content")
                    .slug("some-new-slug")
                    .thumbnailId("124")
                    .comments(List.of("1", "23", "8"))
                    .status(PostStatus.PRIVATE)
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
                    .jsonPath("$.content").value(equalTo(dto.getContent()))
                    .jsonPath("$.slug").value(equalTo(dto.getSlug()))
                    .jsonPath("$.thumbnail_id").value(is(dto.getThumbnailId()))
                    .jsonPath("$.status").value(is(dto.getStatus().toString()))
                    .jsonPath("$.comments").value(is(dto.getComments()))
                    .jsonPath("$.meta").value(equalTo(dto.getMeta()));
        }


        @Test
        void shouldReturnPostByIdOrSlug() {
            var post = postsService.create(CreatePostDTO.builder()
                    .title("test post")
                    .content("some post content")
                    .slug("test-post")
                    .build())
                    .map(converter.convert(PostDTO.class))
                    .block();

            assert post != null;

            client.get()
                    .uri(API.PATH + "/" + post.getId())
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(PostDTO.class).value(is(post));


            client.get()
                    .uri(API.PATH + "/" + post.getSlug())
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(PostDTO.class).value(is(post));
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
            var dto = CreatePostDTO.builder()
                    .content("this is some content")
                    .slug("invalid-post slug")
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
        void shouldDeletePostByIdOrSlug() {
            var post = postsService.create(CreatePostDTO.builder()
                    .title("test post")
                    .content("some post content")
                    .slug("test-post")
                    .build()).block();

            assert post != null;

            client.delete()
                    .uri(API.PATH + "/" + post.getId())
                    .exchange()
                    .expectStatus().isOk();


        }


        @Test
        void shouldReturnPaginatedCollectionOfPosts() {

            client.get()
                    .uri(API.PATH)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().value(Headers.TOTAL_ITEMS, is(total_items.toString()))
                    .expectBody()
                    .jsonPath("$").isArray()
                    .jsonPath("$").value(hasSize(10));

            client.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(API.PATH)
                            .queryParam("limit", "15")
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().value(Headers.TOTAL_ITEMS, is(total_items.toString()))
                    .expectBody()
                    .jsonPath("$").isArray()
                    .jsonPath("$").value(hasSize(15));


            client.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(API.PATH)
                            .queryParam("limit", "20")
                            .queryParam("skip", total_items - 10)
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().value(Headers.TOTAL_ITEMS, is(total_items.toString()))
                    .expectBody()
                    .jsonPath("$").isArray()
                    .jsonPath("$").value(hasSize(10));
        }
    }
}
