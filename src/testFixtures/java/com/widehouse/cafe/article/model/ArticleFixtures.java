package com.widehouse.cafe.article.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.test.util.ReflectionTestUtils;

public class ArticleFixtures {
    /**
     * Article list fixtures.
     * @param board Board
     */
    public static List<Article> articles(Board board, int size) {
        return IntStream.range(1, size + 1)
                .mapToObj(i -> Article.builder()
                        .board(board).title("article" + i).content("content" + i)
                        .createdAt(ZonedDateTime.now()).build())
                .collect(Collectors.toList());
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
