package com.widehouse.cafe.article.model;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Article {
    private String title;
    private String content;
    private Board board;
    private ZonedDateTime createdAt;

    @Builder
    private Article(String title, String content, Board board, ZonedDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.board = board;
        this.createdAt = createdAt;
    }
}
