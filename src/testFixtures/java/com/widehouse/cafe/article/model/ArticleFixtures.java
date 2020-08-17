package com.widehouse.cafe.article.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.test.util.ReflectionTestUtils;

public class ArticleFixtures {
    /**
     * Article list fixtures.
     * @param board Board
     */
    public static List<Article> articles(Board board) {
        var now = ZonedDateTime.now();
        return List.of(
                Article.builder()
                        .title("article1").content("content1").board(board).createdAt(now.minusHours(3)).build(),
                Article.builder()
                        .title("article2").content("content2").board(board).createdAt(now.minusHours(2)).build(),
                Article.builder()
                        .title("article3").content("content3").board(board).createdAt(now.minusHours(1)).build(),
                Article.builder()
                        .title("article4").content("content4").board(board).createdAt(now).build());
    }

    /**
     * Article fixtures.
     * @return Article
     */
    public static Article article() {
        var article = Article.builder()
                .title("article1").content("content1").board(BoardFixtures.board1()).createdAt(ZonedDateTime.now())
                .build();
        ReflectionTestUtils.setField(article, "id", UUID.randomUUID());
        return article;
    }
}
