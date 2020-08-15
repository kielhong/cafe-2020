package com.widehouse.cafe.article.api;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.widehouse.cafe.article.model.ArticleFixtures;
import com.widehouse.cafe.article.model.Board;
import com.widehouse.cafe.article.model.BoardFixtures;
import com.widehouse.cafe.article.service.ArticleService;
import com.widehouse.cafe.article.service.BoardService;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {
    @Autowired
    private MockMvc mvc;
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
}