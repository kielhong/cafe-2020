package com.widehouse.cafe.comment.api;

import com.widehouse.cafe.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postComment(@RequestBody CommentRequest commentRequest) {
        commentService.createComment(commentRequest);
    }

    @DeleteMapping("{commentId}")
    public Mono<Void> deleteComment(@PathVariable String commentId) {
        return commentService.deleteComment(commentId);
    }
}
