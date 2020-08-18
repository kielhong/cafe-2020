package com.widehouse.cafe.comment.model;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CommentRepository extends ReactiveCrudRepository<Comment, String> {
}
