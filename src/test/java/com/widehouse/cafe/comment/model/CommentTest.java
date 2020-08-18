package com.widehouse.cafe.comment.model;

import static org.assertj.core.api.BDDAssertions.then;

import com.widehouse.cafe.comment.api.CommentRequest;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CommentTest {
    @Test
    void build_then_construct() {
        // given
        var articleId = UUID.randomUUID();
        var now = ZonedDateTime.now();
        // when
        var comment = Comment.builder().articleId(articleId).content("comment").createdAt(now).build();
        // then
        then(comment).satisfies(c -> {
            then(c.getContent()).isEqualTo("comment");
            then(c.getArticleId()).isEqualTo(articleId);
            then(c.getCreatedAt()).isEqualTo(now);
            then(c.isDeleted()).isFalse();
        });
    }

    @Test
    void given_request_when_from_then_construct() {
        // given
        var articleId = UUID.randomUUID();
        var commentRequest = new CommentRequest(articleId.toString(), "comment");
        // when
        var comment = Comment.from(commentRequest);
        // then
        then(comment).satisfies(c -> {
            then(c.getContent()).isEqualTo("comment");
            then(c.getArticleId()).isEqualTo(articleId);
            then(c.getCreatedAt()).isNotNull();
            then(c.isDeleted()).isFalse();
        });
    }

    @Test
    void given_comment_when_delete_then_updateDeleteToTrue() {
        // given
        var comment = Comment.builder()
                .articleId(UUID.randomUUID()).content("comment").createdAt(ZonedDateTime.now()).build();
        // when
        comment.delete();
        // then
        then(comment.isDeleted()).isTrue();
    }
}