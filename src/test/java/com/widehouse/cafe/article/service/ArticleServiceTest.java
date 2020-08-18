package com.widehouse.cafe.article.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.widehouse.cafe.article.api.ArticleRequest;
import com.widehouse.cafe.article.model.Article;
import com.widehouse.cafe.article.model.ArticleFixtures;
import com.widehouse.cafe.article.model.ArticleRepository;
import com.widehouse.cafe.article.model.Board;
import com.widehouse.cafe.article.model.BoardFixtures;
import com.widehouse.cafe.article.model.BoardRepository;
import com.widehouse.cafe.cafe.model.CafeFixtures;
import com.widehouse.cafe.common.exception.ArticleNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    private ArticleService service;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private BoardRepository boardRepository;
    @Captor
    private ArgumentCaptor<Article> articleCaptor;

    private Board board;

    @BeforeEach
    void setUp() {
        service = new ArticleService(articleRepository, boardRepository);
        board = BoardFixtures.board1();
    }

    @Test
    void given_article_when_getArticle_then_returnArticle() {
        // given
        var article = ArticleFixtures.article();
        given(articleRepository.findById(any(UUID.class))).willReturn(Optional.of(article));
        // when
        var result = service.getArticle(UUID.randomUUID());
        // then
        then(result)
                .isPresent()
                .hasValue(article);
    }

    @Nested
    class CreateArticle {
        @Test
        void given_request_when_createArticle_then_return_idOfCreatedArticle() {
            // given
            var article = ArticleFixtures.article();
            given(articleRepository.save(any(Article.class))).willReturn(article);
            // when
            var request = new ArticleRequest(1L, "title", "content");
            var result = service.createArticle(board, request);
            // then
            then(result).isEqualTo(article);
        }
    }

    @Nested
    class DeleteArticle {
        @Test
        void given_id_when_delete_then_deleteArticle() {
            // given
            var id = UUID.randomUUID();
            var article = ArticleFixtures.article();
            given(articleRepository.findById(eq(id))).willReturn(Optional.of(article));
            // when
            service.deleteArticle(id);
            // then
            verify(articleRepository).delete(eq(article));
        }

        @Test
        void given_notExistArticle_then_throwArticleNotFoundException() {
            // given
            var id = UUID.randomUUID();
            given(articleRepository.findById(eq(id))).willReturn(Optional.empty());
            // expect
            thenThrownBy(() -> service.deleteArticle(id))
                    .isInstanceOf(ArticleNotFoundException.class);
            verify(articleRepository, never()).delete(any(Article.class));
        }
    }

    @Nested
    class UpdateArticle {
        @Test
        void given_request_when_update_then_updateArticle() {
            // given
            var article = ArticleFixtures.article();
            given(articleRepository.findById(any(UUID.class))).willReturn(Optional.of(article));
            var board2 = BoardFixtures.board2();
            ReflectionTestUtils.setField(board2, "cafe", article.getBoard().getCafe());
            given(boardRepository.findById(anyLong())).willReturn(Optional.of(board2));
            // when
            var request = new ArticleRequest(board2.getId(), "new title", "new content");
            service.updateArticle(UUID.randomUUID(), request);
            // then
            verify(articleRepository).save(articleCaptor.capture());
            then(articleCaptor.getValue()).satisfies(a -> {
                then(a.getBoard()).isEqualTo(board2);
                then(a.getTitle()).isEqualTo("new title");
                then(a.getContent()).isEqualTo("new content");
            });
        }

        @Test
        void given_notExistArticle_then_throwArticleNotFoundException() {
            // given
            given(articleRepository.findById(any(UUID.class))).willReturn(Optional.empty());
            // expect
            var request = new ArticleRequest(1L, "new title", "new content");
            thenThrownBy(() -> service.updateArticle(UUID.randomUUID(), request))
                    .isInstanceOf(ArticleNotFoundException.class);
            verify(articleRepository, never()).save(any(Article.class));
        }
    }

    @Nested
    class UpdateBoardOfArticle {
        @Test
        void given_request_when_updateSameBoard_then_doesNotUpdateBoard() {
            // given
            var article = ArticleFixtures.article();
            given(articleRepository.findById(any(UUID.class))).willReturn(Optional.of(article));
            var board = article.getBoard();
            // when
            var request = new ArticleRequest(board.getId(), "new title", "new content");
            service.updateArticle(UUID.randomUUID(), request);
            // then
            verify(articleRepository).save(articleCaptor.capture());
            then(articleCaptor.getValue()).satisfies(a -> {
                then(a.getBoard()).isEqualTo(board);
                then(a.getTitle()).isEqualTo("new title");
                then(a.getContent()).isEqualTo("new content");
            });
        }

        @Test
        void given_nonExistBoard_when_updateBoard_then_throwIllegalArgumentException() {
            // given
            given(articleRepository.findById(any(UUID.class))).willReturn(Optional.of(ArticleFixtures.article()));
            given(boardRepository.findById(anyLong())).willReturn(Optional.empty());
            // when
            var request = new ArticleRequest(2L, "new title", "new content");
            thenThrownBy(() -> service.updateArticle(UUID.randomUUID(), request))
                    .isInstanceOf(IllegalArgumentException.class);
            // then
            verify(articleRepository, never()).save(any(Article.class));
        }

        @Test
        void given_boardInAnotherCafe_then_throwIllegalArgumentException() {
            // given
            given(articleRepository.findById(any(UUID.class))).willReturn(Optional.of(ArticleFixtures.article()));
            var board2 = BoardFixtures.board2();
            ReflectionTestUtils.setField(board2, "cafe", CafeFixtures.bar());
            given(boardRepository.findById(anyLong())).willReturn(Optional.of(board2));
            // when
            var request = new ArticleRequest(board2.getId(), "new title", "new content");
            thenThrownBy(() -> service.updateArticle(UUID.randomUUID(), request))
                    .isInstanceOf(IllegalArgumentException.class);
            // then
            verify(articleRepository, never()).save(any(Article.class));
        }
    }
}