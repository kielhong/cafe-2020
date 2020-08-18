package com.widehouse.cafe.comment.service;

import com.widehouse.cafe.comment.api.CommentRequest;
import com.widehouse.cafe.comment.model.Comment;
import com.widehouse.cafe.comment.model.CommentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Mono<Comment> createComment(CommentRequest commentRequest) {
        return commentRepository.save(Comment.from(commentRequest));
    }
}
