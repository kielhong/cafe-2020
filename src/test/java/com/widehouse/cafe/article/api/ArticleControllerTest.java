package com.widehouse.cafe.article.api;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.widehouse.cafe.article.model.ArticleFixtures;
import com.widehouse.cafe.article.model.Board;
import com.widehouse.cafe.article.model.BoardFixtures;
import com.widehouse.cafe.article.service.ArticleService;
import com.widehouse.cafe.article.service.BoardService;
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
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

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
    @DisplayName("GET /api/cafes/{nickname}/boards/{boardId}/articles")
    class ListArticle {
        @Test
        void given_articles_then_listArticles() throws Exception {
            // given
            var board = BoardFixtures.board1();
            given(boardService.getBoard(anyLong())).willReturn(Optional.of(board));
            var articles = ArticleFixtures.articles(board);
            given(articleService.listByBoard(any(Board.class))).willReturn(articles);
            // when
            mvc.perform(get("/api/cafes/{nickname}/boards/{id}/articles", "foo", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(articles.size())));
        }

        @Test
        void given_notExistBoard_then_listEmpties() throws Exception {
            // given
            given(boardService.getBoard(anyLong())).willReturn(Optional.empty());
            // when
            mvc.perform(get("/api/cafes/{nickname}/boards/{id}/articles", "foo", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(0)));
        }
    }

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
}