package com.widehouse.cafe.article.model;

import static org.assertj.core.api.BDDAssertions.then;

import com.widehouse.cafe.article.api.ArticleRequest;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class ArticleTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = BoardFixtures.board1();
        ReflectionTestUtils.setField(board, "id", 1L);
    }

    @Test
    void when_build_then_create() {
        // when
        var now = ZonedDateTime.now();
        var result = Article.builder().title("title").content("content").board(board).createdAt(now).build();
        // then
        then(result)
                .hasFieldOrPropertyWithValue("title", "title")
                .hasFieldOrPropertyWithValue("content", "content")
                .hasFieldOrPropertyWithValue("board", board)
                .hasFieldOrPropertyWithValue("createdAt", now);
    }

    @Test
    void when_from_then_create() {
        // given
        var request = new ArticleRequest(board.getId(), "title", "content");
        // when
        var result = Article.from(board, request);
        // then
        then(result)
                .hasFieldOrPropertyWithValue("title", "title")
                .hasFieldOrPropertyWithValue("content", "content")
                .hasFieldOrPropertyWithValue("board", board)
                .hasFieldOrProperty("createdAt");
    }

    @Test
    void getContent_thenArticleContentBody() {
        // when
        var result = Article.builder()
                .title("title").content("content").board(board).createdAt(ZonedDateTime.now()).build();
        // then
        then(result.getContent())
                .isEqualTo("content");
    }

    @Test
    void update_then_updateTitleAndContent() {
        // given
        var article = ArticleFixtures.article();
        var board2 = BoardFixtures.board2();
        // when
        article.update(board2, "new title", "new content");
        // then
        then(article).satisfies(a -> {
            then(a.getBoard()).isEqualTo(board2);
            then(a.getTitle()).isEqualTo("new title");
            then(a.getContent()).isEqualTo("new content");
        });
    }
}