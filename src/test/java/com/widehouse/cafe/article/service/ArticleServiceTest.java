package com.widehouse.cafe.article.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
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
import com.widehouse.cafe.exception.ArticleNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    private ArticleService service;
    @Mock
    private ArticleRepository articleRepository;

    private Board board;

    @BeforeEach
    void setUp() {
        service = new ArticleService(articleRepository);
        board = BoardFixtures.board1();
    }

    @Test
    void given_articles_when_listByBoard_then_listArticles() {
        // given
        var articles = ArticleFixtures.articles(board);
        given(articleRepository.findByBoard(any(Board.class))).willReturn(articles);
        // when
        var result = service.listByBoard(board);
        // then
        then(result).isEqualTo(articles);
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

}