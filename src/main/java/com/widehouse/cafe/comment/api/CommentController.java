package com.widehouse.cafe.comment.api;

import com.widehouse.cafe.comment.model.Comment;
import com.widehouse.cafe.comment.service.CommentService;
import com.widehouse.cafe.common.dto.ResultResponse;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(params = "articleId")
    public Flux<Comment> listComments(@RequestParam UUID articleId) {
        return commentService.listComment(articleId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResultResponse> postComment(@RequestBody CommentRequest commentRequest) {
        return commentService.createComment(commentRequest)
                .map(comment -> new ResultResponse<>(comment.getId()));
    }

    @DeleteMapping("{commentId}")
    public Mono<Void> deleteComment(@PathVariable String commentId) {
        return commentService.deleteComment(commentId);
    }
}
