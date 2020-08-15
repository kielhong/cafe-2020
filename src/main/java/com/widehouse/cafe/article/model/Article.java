package com.widehouse.cafe.article.model;

import com.widehouse.cafe.article.api.ArticleRequest;
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

    /**
     * create Article from article request.
     * @return created Article
     */
    public static Article from(Board board, ArticleRequest request) {
        return Article.builder()
                .board(board)
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(ZonedDateTime.now())
                .build();
    }
}
