package com.widehouse.cafe.comment.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.widehouse.cafe.comment.api.CommentRequest;
import com.widehouse.cafe.comment.model.Comment;
import com.widehouse.cafe.comment.model.CommentFixtures;
import com.widehouse.cafe.comment.model.CommentRepository;
import com.widehouse.cafe.common.exception.CommentNotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    private CommentService service;
    @Mock
    private CommentRepository commentRepository;
    @Captor
    private ArgumentCaptor<Comment> commentCaptor;

    @BeforeEach
    void setUp() {
        service = new CommentService(commentRepository);
    }

    @Test
    void given_request_when_create_thenCreateAndReturnComment() {
        // given
        var articleId = UUID.randomUUID();
        var comment = Comment.builder().articleId(articleId).content("comment").build();
        given(commentRepository.save(any(Comment.class))).willReturn(Mono.just(comment));
        // when
        var result = service.createComment(new CommentRequest(articleId.toString(), "comment"));
        // then
        StepVerifier.create(result)
                .expectNext(comment)
                .verifyComplete();
        verify(commentRepository).save(commentCaptor.capture());
        then(commentCaptor.getValue()).satisfies(c -> {
            then(c.getArticleId()).isEqualTo(articleId);
            then(c.getContent()).isEqualTo("comment");
        });
    }

    @Test
    void given_id_when_delete_thenSoftDeleteComment() {
        // given
        var comment = Comment.builder().articleId(UUID.randomUUID()).content("comment").build();
        given(commentRepository.findById(anyString())).willReturn(Mono.just(comment));
        given(commentRepository.save(any(Comment.class))).willReturn(Mono.just(comment)); // dummy mono
        // when
        var result = service.deleteComment("123456");
        // then
        StepVerifier.create(result).verifyComplete();
        verify(commentRepository).save(commentCaptor.capture());
        then(commentCaptor.getValue().isDeleted()).isTrue();
    }

    @Test
    void given_notExistComment_when_delete_then_MonoErrorWithCommentNotFoundException() {
        // given
        given(commentRepository.findById(anyString())).willReturn(Mono.empty());
        // when
        var result = service.deleteComment("123456");
        // then
        StepVerifier.create(result)
                .expectError(CommentNotFoundException.class)
                .verify();
    }

    @Test
    void given_commentsOfArticle_when_list_then_FluxComments() {
        // given
        var articleId = UUID.randomUUID();
        var comments = CommentFixtures.comments(articleId, 5);
        given(commentRepository.findAllByArticleId(eq(articleId))).willReturn(Flux.fromIterable(comments));
        // when
        var result = service.listComment(articleId);
        // then
        StepVerifier.create(result.log())
                .expectNext(comments.get(0))
                .expectNext(comments.get(1))
                .expectNext(comments.get(2))
                .expectNext(comments.get(3))
                .expectNext(comments.get(4))
                .verifyComplete();
    }
}