package com.widehouse.cafe.article.api;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.widehouse.cafe.article.model.Article;
import com.widehouse.cafe.article.model.ArticleFixtures;
import com.widehouse.cafe.article.model.BoardFixtures;
import com.widehouse.cafe.article.service.ArticleListService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ArticleListController.class)
class ArticleListControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ArticleListService articleListService;

    List<Article> articles;

    @Nested
    @DisplayName("GET /api/articles?boardId={boardId}")
    class ArticleListByBoard {
        @Test
        void given_articles_when_listByBoard_thenListSameBoardArticles() throws Exception {
            // given
            var articles = ArticleFixtures.articles(BoardFixtures.board1(), 5);
            given(articleListService.listByBoard(anyLong(), any())).willReturn(new SliceImpl<>(articles));
            // when
            mvc.perform(get("/api/articles")
                    .param("boardId", "1")
                    .param("size", "5"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(5)));
        }
    }
}