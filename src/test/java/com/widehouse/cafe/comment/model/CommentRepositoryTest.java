package com.widehouse.cafe.comment.model;

import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.test.StepVerifier;

@DataMongoTest
class CommentRepositoryTest {
    @Autowired
    private ReactiveMongoTemplate template;
    @Autowired
    private CommentRepository repository;

    @Test
    void findByArticleId() {
        // given
        var articleId = UUID.randomUUID();
        IntStream.range(1, 6)
                .forEach(i -> template.save(
                        Comment.builder().articleId(articleId).content("comment" + i).build()).block());
        IntStream.range(1, 5)
                .forEach(i -> template.save(
                        Comment.builder().articleId(UUID.randomUUID()).content("other comment").build()).block());
        // when
        var result = repository.findAllByArticleId(articleId);
        // then
        StepVerifier.create(result)
                .expectNextCount(5)
                .thenConsumeWhile(c -> c.getArticleId().equals(articleId))
                .verifyComplete();
    }
}