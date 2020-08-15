package com.widehouse.cafe.article.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.widehouse.cafe.article.model.Board;
import com.widehouse.cafe.article.model.BoardFixtures;
import com.widehouse.cafe.article.model.BoardRepository;
import com.widehouse.cafe.cafe.model.Cafe;
import com.widehouse.cafe.cafe.model.CafeFixtures;
import java.util.List;
import java.util.Optional;
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

    @Test
    void given_boardsOfCafe_when_listByCafe_then_returnOrderedList() {
        // given
        var board1 = Board.builder().name("board1").description("desc1").cafe(cafe).order(3).build();
        var board2 = Board.builder().name("board2").description("desc2").cafe(cafe).order(2).build();
        var board3 = Board.builder().name("board3").description("desc3").cafe(cafe).order(1).build();
        given(boardRepository.findByCafe(any(Cafe.class)))
                .willReturn(List.of(board1, board2, board3));
        // when
        var result = service.listByCafe(cafe);
        // then
        then(result).containsExactly(board3, board2, board1);
    }

    @Test
    void given_boardsOfCafe_when_getBoard_then_returnBoard() {
        // given
        var board = BoardFixtures.board1(cafe);
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));
        // when
        var result = service.getBoard(1L);
        // then
        then(result)
                .isPresent()
                .hasValue(board);
    }
}