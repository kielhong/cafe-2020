package com.widehouse.cafe.comment.model;

import com.widehouse.cafe.comment.api.CommentRequest;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Document
@Getter
public class Comment {
    @Id
    private String id;

    private UUID articleId;
    private String content;
    private LocalDateTime createdAt;
    private boolean deleted;

    @Builder
    private Comment(UUID articleId, String content, LocalDateTime createdAt) {
        this.articleId = articleId;
        this.content = content;
        this.createdAt = createdAt;
        this.deleted = false;
    }

    /**
     * static constructor from CommentRequest.
     */
    public static Comment from(CommentRequest request) {
        return Comment.builder()
                .articleId(UUID.fromString(request.getArticleId()))
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void delete() {
        this.deleted = true;
    }
}
