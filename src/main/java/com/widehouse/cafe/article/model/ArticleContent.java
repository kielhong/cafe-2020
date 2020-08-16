package com.widehouse.cafe.article.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class ArticleContent {
    @Id
    private UUID id;
    @OneToOne
    @MapsId
    private Article article;
    private String body;

    public ArticleContent(Article article, String body) {
        this.article = article;
        this.body = body;
    }

    public void updateBody(String body) {
        this.body = body;
    }
}
