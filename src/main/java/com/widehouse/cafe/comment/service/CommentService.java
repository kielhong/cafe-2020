package com.widehouse.cafe.comment.service;

import com.widehouse.cafe.comment.api.CommentRequest;
import com.widehouse.cafe.comment.model.Comment;
import com.widehouse.cafe.comment.model.CommentRepository;
import com.widehouse.cafe.common.exception.CommentNotFoundException;
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

    /**
     * softly delete a comment.
     * @param commentId id of comment to delete
     */
    public Mono<Void> deleteComment(String commentId) {
        return commentRepository.findById(commentId)
                .map(comment -> {
                    comment.delete();
                    return comment;
                })
                .flatMap(commentRepository::save)
                .switchIfEmpty(Mono.error(new CommentNotFoundException(commentId)))
                .then();
    }
}
