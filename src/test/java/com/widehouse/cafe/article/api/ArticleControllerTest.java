package com.widehouse.cafe.article.api;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.widehouse.cafe.article.model.ArticleFixtures;
import com.widehouse.cafe.article.model.Board;
import com.widehouse.cafe.article.model.BoardFixtures;
import com.widehouse.cafe.article.service.ArticleService;
import com.widehouse.cafe.article.service.BoardService;
import com.widehouse.cafe.exception.ArticleNotFoundException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private BoardService boardService;
    @MockBean
    private ArticleService articleService;

    @Nested
    @DisplayName("GET /api/articles/{id}")
    class GetArticle {
        @Test
        @DisplayName("given article of id then return a article")
        void given_Article_then_returnArticle() throws Exception {
            // given
            var article = ArticleFixtures.article();
            given(articleService.getArticle(any(UUID.class))).willReturn(Optional.of(article));
            // when
            mvc.perform(get("/api/articles/{id}", UUID.randomUUID()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value(article.getTitle()))
                    .andExpect(jsonPath("$.content").value(article.getContent()));
        }

        @Test
        @DisplayName("given not exists Article then 404 NotFound")
        void given_notExistsArticle_then_404NotFound() throws Exception {
            // given
            given(articleService.getArticle(any(UUID.class))).willReturn(Optional.empty());
            // when
            mvc.perform(get("/api/articles/{id}", UUID.randomUUID()))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("POST /api/articles")
    class CreateArticle {
        @Test
        void given_request_when_post_then_returnCreatedArticleId() throws Exception {
            // given
            given(boardService.getBoard(anyLong())).willReturn(Optional.of(BoardFixtures.board1()));
            var article = ArticleFixtures.article();
            ReflectionTestUtils.setField(article, "id", UUID.randomUUID());
            given(articleService.createArticle(any(Board.class), any(ArticleRequest.class))).willReturn(article);
            // when
            Map<String, String> body = Map.of("boardId", String.valueOf(1L), "title", "title", "content", "content");
            mvc.perform(post("/api/articles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(article.getId().toString()));
        }

        @Test
        void given_notExistBoard_when_post_then_400BadRequest() throws Exception {
            // given
            given(boardService.getBoard(anyLong())).willReturn(Optional.empty());
            // when
            var body = Map.of("boardId", String.valueOf(0L), "title", "title", "content", "content");
            mvc.perform(post("/api/articles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(body)))
                    .andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @MethodSource("articleParamProvider")
        void given_invalid_request_when_post_then_400BadRequest(String title, String content) throws Exception {
            // given
            given(boardService.getBoard(anyLong())).willReturn(Optional.of(BoardFixtures.board1()));
            // when
            var body = Map.of("boardId", String.valueOf(1L), "title", title, "content", content);
            mvc.perform(post("/api/articles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(body)))
                    .andExpect(status().isBadRequest());
        }

        Stream<Arguments> articleParamProvider() {
            return Stream.of(
                    arguments("title", ""),
                    arguments("", "content"));
        }
    }

    @Nested
    @DisplayName("DELETE /api/articles/{id}")
    class DeleteArticle {
        @Test
        void given_article_then_delete() throws Exception {
            // given
            final var id = UUID.randomUUID();
            // when
            mvc.perform(delete("/api/articles/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id.toString()));
            // then
            verify(articleService).deleteArticle(eq(id));
        }

        @Test
        void given_notExistArticle_then_404NotFound() throws Exception {
            // given
            final var id = UUID.randomUUID();
            willThrow(ArticleNotFoundException.class)
                    .given(articleService).deleteArticle(id);
            // when
            mvc.perform(delete("/api/articles/{id}", id))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("PUT /api/articles/{id}")
    class UpdateArticle {
        @Test
        void given_request_then_updateArticle() throws Exception {
            // given
            final var id = UUID.randomUUID();
            // when
            Map<String, String> body = Map.of("boardId", "2", "title", "new title", "content", "new content");
            mvc.perform(put("/api/articles/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id.toString()));
            verify(articleService).updateArticle(eq(id), any(ArticleRequest.class));
        }

        @Test
        void given_notExistArticle_when_update_then_throw404NotFound() throws Exception {
            // given
            final var id = UUID.randomUUID();
            willThrow(new ArticleNotFoundException(id))
                    .given(articleService).updateArticle(eq(id), any(ArticleRequest.class));
            // when
            Map<String, String> body = Map.of("title", "new title", "content", "new content");
            mvc.perform(put("/api/articles/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(body)))
                    .andExpect(status().isNotFound());
        }

        @Test
        void given_invalidBoard_when_update_then_throw400BadRequest() throws Exception {
            // given
            final var id = UUID.randomUUID();
            willThrow(new IllegalArgumentException())
                    .given(articleService).updateArticle(eq(id), any(ArticleRequest.class));
            // when
            Map<String, String> body = Map.of("boardId", "-1", "title", "new title", "content", "new content");
            mvc.perform(put("/api/articles/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(body)))
                    .andExpect(status().isBadRequest());
        }
    }
}