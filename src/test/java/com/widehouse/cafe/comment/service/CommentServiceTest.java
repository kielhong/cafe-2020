package com.widehouse.cafe.comment.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.widehouse.cafe.comment.api.CommentRequest;
import com.widehouse.cafe.comment.model.Comment;
import com.widehouse.cafe.comment.model.CommentRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
}