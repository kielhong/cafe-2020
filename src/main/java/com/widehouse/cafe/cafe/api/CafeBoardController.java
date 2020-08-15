package com.widehouse.cafe.cafe.api;

import com.widehouse.cafe.article.model.Board;
import com.widehouse.cafe.article.service.BoardService;
import com.widehouse.cafe.cafe.service.CafeService;
import com.widehouse.cafe.exception.BoardNotFoundException;
import com.widehouse.cafe.exception.CafeNotFoundException;
import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cafes/{nickname}")
public class CafeBoardController {
    private CafeService cafeService;
    private BoardService boardService;

    public CafeBoardController(CafeService cafeService, BoardService boardService) {
        this.cafeService = cafeService;
        this.boardService = boardService;
    }

    /**
     * Get /api/cafes/{nickname}/boards.
     * List Boards of Cafe
     * @param nickname nickname of cafe
     */
    @GetMapping("boards")
    public List<Board> listBoards(@PathVariable String nickname) {
        return cafeService.getCafe(nickname)
                .map(cafe -> boardService.listByCafe(cafe))
                .orElse(Collections.emptyList());
    }

    /**
     * GET /api/cafes/{nickname}/boards/{id}.
     * Get a Board of Cafe
     * @param nickname nickname of cafe
     * @param id id of board
     */
    @GetMapping("boards/{id}")
    public Board getBoard(@PathVariable String nickname, @PathVariable Long id) {
        cafeService.getCafe(nickname).orElseThrow(() -> new CafeNotFoundException(nickname));

        return boardService.getBoard(id)
                .orElseThrow(() -> new BoardNotFoundException(id));
    }
}
