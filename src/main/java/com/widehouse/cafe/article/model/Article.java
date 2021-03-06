package com.widehouse.cafe.article.model;

import com.widehouse.cafe.article.api.ArticleRequest;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@NoArgsConstructor
@Getter
public class Article {
    @Id @GeneratedValue
    private UUID id;
    private String title;
    @OneToOne(mappedBy = "article", cascade = CascadeType.ALL, optional = false)
    private ArticleContent content;
    @ManyToOne
    private Board board;
    private ZonedDateTime createdAt;

    @Builder
    private Article(String title, String content, Board board, ZonedDateTime createdAt) {
        Assert.hasText(title, "title must not be blank");
        Assert.hasText(content, "content must not be blank");
        Assert.notNull(board, "article must have parent board");
        Assert.notNull(createdAt, "article must have created timestamp");

        this.title = title;
        this.content = new ArticleContent(this, content);
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

    /**
     * update Article data.
     * @param board board of article
     * @param title new title of article
     * @param content new content of article
     */
    public void update(Board board, String title, String content) {
        this.board = board;
        this.title = title;
        this.content.updateBody(content);
    }

    public String getContent() {
        return content.getBody();
    }
}
