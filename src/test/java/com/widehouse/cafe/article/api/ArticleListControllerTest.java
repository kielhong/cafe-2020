package com.widehouse.cafe.article.api;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.widehouse.cafe.article.model.ArticleFixtures;
import com.widehouse.cafe.article.model.BoardFixtures;
import com.widehouse.cafe.article.service.ArticleListService;
import com.widehouse.cafe.cafe.model.CafeFixtures;
import com.widehouse.cafe.cafe.service.CafeService;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(ArticleListController.class)
class ArticleListControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ArticleListService articleListService;
    @MockBean
    private CafeService cafeService;

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

    @Nested
    @DisplayName("GET /api/cafes/{nickname}/articles")
    class ArticleListByCafe {
        @Test
        void given_articles_when_listByCafe_thenListCafeArticles() throws Exception {
            // given
            var cafe = CafeFixtures.foo();
            given(cafeService.getCafe(anyString())).willReturn(Optional.of(cafe));
            var articles = new ArrayList<>(ArticleFixtures.articles(BoardFixtures.board1(), 3));
            articles.addAll(ArticleFixtures.articles(BoardFixtures.board2(), 3));
            given(articleListService.listByCafe(eq(cafe), any())).willReturn(new SliceImpl<>(articles));
            // when
            mvc.perform(get("/api/cafes/{nickname}/articles", "foo")
                    .param("page", "1")
                    .param("size", "6"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(6)));
        }

        @Test
        void given_invalidCafe_when_listByCafe_thenEmptyList() throws Exception {
            // given
            given(cafeService.getCafe(anyString())).willReturn(Optional.empty());
            // when
            mvc.perform(get("/api/cafes/{nickname}/articles", "foo")
                    .param("page", "1")
                    .param("size", "5"))
                    .andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(jsonPath("$.content", hasSize(0)))
                    .andExpect(jsonPath("$.empty").value(true));
        }
    }
}