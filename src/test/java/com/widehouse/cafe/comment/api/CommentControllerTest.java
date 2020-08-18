package com.widehouse.cafe.comment.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.widehouse.cafe.comment.model.Comment;
import com.widehouse.cafe.comment.service.CommentService;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(CommentController.class)
class CommentControllerTest {
    @Autowired
    private WebTestClient client;
    @MockBean
    private CommentService commentService;

    @Nested
    class PostComment {
        @Test
        void given_content_when_post_then_createComment() {
            // given
            var articleId = UUID.randomUUID();
            var comment = Comment.builder().articleId(articleId).content("comment").build();
            given(commentService.createComment(any(CommentRequest.class))).willReturn(Mono.just(comment));
            // when
            var body = new CommentRequest(articleId.toString(), "comment");
            client.post().uri("/api/comments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(body), CommentRequest.class)
                    .exchange()
                    .expectStatus().isCreated();
            verify(commentService).createComment(any(CommentRequest.class));
        }
    }
}