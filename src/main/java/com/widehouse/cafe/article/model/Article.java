package com.widehouse.cafe.article.model;

import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Article {
    @Id @GeneratedValue
    private UUID id;
    private String title;
    private String content;
    @ManyToOne
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
