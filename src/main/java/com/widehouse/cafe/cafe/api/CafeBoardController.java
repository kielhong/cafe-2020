package com.widehouse.cafe.cafe.api;

import com.widehouse.cafe.article.model.Board;
import com.widehouse.cafe.article.service.BoardService;
import com.widehouse.cafe.cafe.service.CafeService;
import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cafes")
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
    @GetMapping("{nickname}/boards")
    public List<Board> listBoards(@PathVariable String nickname) {
        return cafeService.getCafe(nickname)
                .map(cafe -> boardService.listByCafe(cafe))
                .orElse(Collections.emptyList());
    }
}
