package com.widehouse.cafe.comment.model;

import com.widehouse.cafe.comment.api.CommentRequest;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
public class Comment {
    @Id
    private String id;

    private UUID articleId;
    private String content;
    private ZonedDateTime createdAt;

    @Builder
    private Comment(UUID articleId, String content, ZonedDateTime createdAt) {
        this.articleId = articleId;
        this.content = content;
        this.createdAt = createdAt;
    }

    /**
     * static constructor from CommentRequest.
     */
    public static Comment from(CommentRequest request) {
        return Comment.builder()
                .articleId(UUID.fromString(request.getArticleId()))
                .content(request.getContent())
                .createdAt(ZonedDateTime.now())
                .build();
    }
}
