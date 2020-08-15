package com.widehouse.cafe.article.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.widehouse.cafe.article.model.BoardFixtures;
import com.widehouse.cafe.article.model.BoardRepository;
import com.widehouse.cafe.cafe.model.Cafe;
import com.widehouse.cafe.cafe.model.CafeFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
class BoardServiceTest {
    private BoardService service;
    @Mock
    private BoardRepository boardRepository;

    private Cafe cafe;

    @BeforeEach
    void setUp() {
        service = new BoardService(boardRepository);
        cafe = CafeFixtures.foo();
    }

    @Test
    void given_boardsOfCafe_when_listByCafe_then_returnBoardList() {
        // given
        var boards = BoardFixtures.boards(cafe);
        given(boardRepository.findByCafe(any(Cafe.class))).willReturn(boards);
        // when
        var result = service.listByCafe(cafe);
        // then
        then(result).isEqualTo(boards);
    }
}