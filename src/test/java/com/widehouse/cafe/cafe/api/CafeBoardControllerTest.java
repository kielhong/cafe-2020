package com.widehouse.cafe.cafe.api;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void given_boardsOfCafe_when_get_boardsByCafe_then_listBoards() throws Exception {
        // given
        var cafe = CafeFixtures.foo();
        given(cafeService.getCafe(anyString())).willReturn(Optional.of(cafe));
        var boards = BoardFixtures.boards(cafe);
        given(boardService.listByCafe(any(Cafe.class))).willReturn(boards);
        // when
        this.mvc.perform(get("/api/cafes/{nickname}/boards", "foo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(boards.size())));
    }
}