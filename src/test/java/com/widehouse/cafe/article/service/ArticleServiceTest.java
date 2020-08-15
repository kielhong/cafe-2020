package com.widehouse.cafe.article.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.widehouse.cafe.article.model.ArticleFixtures;
import com.widehouse.cafe.article.model.ArticleRepository;
import com.widehouse.cafe.article.model.Board;
import com.widehouse.cafe.article.model.BoardFixtures;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
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
}