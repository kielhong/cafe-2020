package com.widehouse.cafe.comment.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.widehouse.cafe.comment.model.Comment;
import com.widehouse.cafe.comment.service.CommentService;
import com.widehouse.cafe.common.exception.CommentNotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(CommentController.class)
class CommentControllerTest {
    @Autowired
    private WebTestClient client;
    @MockBean
    private CommentService commentService;

    @Nested
    @DisplayName("POST /api/comments")
    class CreateComment {
        @Test
        void given_content_when_post_then_createComment() {
            // given
            var articleId = UUID.randomUUID();
            var comment = Comment.builder().articleId(articleId).content("comment").build();
            ReflectionTestUtils.setField(comment, "id", "123456");
            given(commentService.createComment(any(CommentRequest.class))).willReturn(Mono.just(comment));
            // when
            var body = new CommentRequest(articleId.toString(), "comment");
            client.post().uri("/api/comments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(body), CommentRequest.class)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody()
                    .jsonPath("$.id").isEqualTo("123456");
            verify(commentService).createComment(any(CommentRequest.class));
        }
    }

    @Nested
    @DisplayName("DELETE /api/comments/{id}")
    class DeleteComment {
        @Test
        void given_commentId_when_delete_then_softDeleteComment() {
            // given
            var commentId = "123456";
            // when
            client.delete().uri("/api/comments/{id}", commentId)
                    .exchange()
                    .expectStatus().isOk();
            verify(commentService).deleteComment(eq(commentId));
        }

        @Test
        void given_notExistComment_when_delete_then_404NotFound() {
            // given
            given(commentService.deleteComment(anyString()))
                    .willReturn(Mono.error(new CommentNotFoundException("123456")));
            // when
            client.delete().uri("/api/comments/{id}", "123456")
                    .exchange()
                    .expectStatus().isNotFound();
        }
    }
}