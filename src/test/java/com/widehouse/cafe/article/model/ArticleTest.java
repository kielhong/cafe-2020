package com.widehouse.cafe.article.model;

import static org.assertj.core.api.BDDAssertions.then;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class ArticleTest {
    @Test
    void when_build_then_create() {
        var board = BoardFixtures.board1();
        var now = ZonedDateTime.now();
        var article = Article.builder().title("title").content("content").board(board).createdAt(now).build();
        // then
        then(article)
                .hasFieldOrPropertyWithValue("title", "title")
                .hasFieldOrPropertyWithValue("content", "content")
                .hasFieldOrPropertyWithValue("board", board)
                .hasFieldOrPropertyWithValue("createdAt", now);
    }
}