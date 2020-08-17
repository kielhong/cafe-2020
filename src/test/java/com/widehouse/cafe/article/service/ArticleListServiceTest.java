package com.widehouse.cafe.article.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.widehouse.cafe.article.model.ArticleFixtures;
import com.widehouse.cafe.article.model.ArticleRepository;
import com.widehouse.cafe.article.model.BoardFixtures;
import com.widehouse.cafe.article.model.BoardRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;

@ExtendWith(MockitoExtension.class)
class ArticleListServiceTest {
    private ArticleListService service;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        service = new ArticleListService(articleRepository, boardRepository);
    }

    @Nested
    class BoardList {
        @Test
        void given_board_then_listWithPaging() {
            // given
            var board = BoardFixtures.board1();
            given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));
            var articles = ArticleFixtures.articles(board, 5);
            given(articleRepository.findByBoard(eq(board), any())).willReturn(new SliceImpl<>(articles));
            // when
            var result = service.listByBoard(1L, PageRequest.of(1, 5));
            // then
            then(result.getContent()).isEqualTo(articles);
        }

        @Test
        void given_invalidBoard_then_emptyList() {
            // given
            given(boardRepository.findById(anyLong())).willReturn(Optional.empty());
            // when
            var result = service.listByBoard(-1L, PageRequest.of(1, 5));
            // then
            then(result.getContent().size()).isEqualTo(0);
        }
    }
}