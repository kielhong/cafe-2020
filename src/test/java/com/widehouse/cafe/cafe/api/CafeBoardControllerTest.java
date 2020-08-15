package com.widehouse.cafe.cafe.api;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.widehouse.cafe.article.model.BoardFixtures;
import com.widehouse.cafe.article.service.BoardService;
import com.widehouse.cafe.cafe.model.Cafe;
import com.widehouse.cafe.cafe.model.CafeFixtures;
import com.widehouse.cafe.cafe.service.CafeService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CafeBoardController.class)
class CafeBoardControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private CafeService cafeService;
    @MockBean
    private BoardService boardService;

    @Nested
    @DisplayName("GET /api/cafes/{nickname}/boards")
    class ListBoards {
        @Test
        void given_boardsOfCafe_when_get_boardsByCafe_then_listBoards() throws Exception {
            // given
            var cafe = CafeFixtures.foo();
            given(cafeService.getCafe(anyString())).willReturn(Optional.of(cafe));
            var boards = BoardFixtures.boards(cafe);
            given(boardService.listByCafe(any(Cafe.class))).willReturn(boards);
            // when
            mvc.perform(get("/api/cafes/{nickname}/boards", "foo"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(boards.size())));
        }

        @Test
        void given_notExistCafe_when_get_boardsByCafe_then_emptyList() throws Exception {
            // given
            given(cafeService.getCafe(anyString())).willReturn(Optional.empty());
            // when
            mvc.perform(get("/api/cafes/{nickname}/boards", "foo"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(0)));
        }
    }

    @Nested
    @DisplayName("GET /api/cafes/{nickname}/boards/{id}")
    class GetBoard {
        @Test
        void given_boardsOfCafe_when_get_board_then_returnBoard() throws Exception {
            // given
            var cafe = CafeFixtures.foo();
            given(cafeService.getCafe(anyString())).willReturn(Optional.of(cafe));
            var board = BoardFixtures.board1(cafe);
            given(boardService.getBoard(anyLong())).willReturn(Optional.of(board));
            // when
            mvc.perform(get("/api/cafes/{nickname}/boards/{id}", "foo", "1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("name").value(board.getName()))
                    .andExpect(jsonPath("cafe.nickname").value(cafe.getNickname()));
        }

        @Test
        void given_notExistsCafe_when_get_Board_throw_404NotFound() throws Exception {
            // given
            given(cafeService.getCafe(anyString())).willReturn(Optional.empty());
            // when
            mvc.perform(get("/api/cafes/{nickname}/boards/{id}", "foo", "1"))
                    .andExpect(status().isNotFound());
        }

        @Test
        void given_notExistsBoard_when_get_Board_throw_404NotFound() throws Exception {
            // given
            given(cafeService.getCafe(anyString())).willReturn(Optional.of(CafeFixtures.foo()));
            given(boardService.getBoard(anyLong())).willReturn(Optional.empty());
            // when
            mvc.perform(get("/api/cafes/{nickname}/boards/{id}", "foo", "1"))
                    .andExpect(status().isNotFound());
        }
    }

}