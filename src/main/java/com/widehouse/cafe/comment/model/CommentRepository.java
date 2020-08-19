package com.widehouse.cafe.comment.model;

import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CommentRepository extends ReactiveCrudRepository<Comment, String> {
    Flux<Comment> findAllByArticleId(UUID articleId);
}
