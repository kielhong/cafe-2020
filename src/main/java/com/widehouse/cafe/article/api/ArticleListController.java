package com.widehouse.cafe.article.api;

import com.widehouse.cafe.article.model.Article;
import com.widehouse.cafe.article.service.ArticleListService;
import com.widehouse.cafe.article.service.BoardService;
import com.widehouse.cafe.cafe.service.CafeService;
import java.util.Collections;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ArticleListController {
    private final ArticleListService articleListService;
    private final BoardService boardService;
    private final CafeService cafeService;

    /**
     * ArticleListController Constructor.
     */
    public ArticleListController(ArticleListService articleListService, BoardService boardService,
                                 CafeService cafeService) {
        this.articleListService = articleListService;
        this.boardService = boardService;
        this.cafeService = cafeService;
    }

    /**
     * GET /api/boards/{boardId}/articles - list articles by board.
     * @param boardId id of board
     * @param pageable paging info
     * @return Slice of article list
     */
    @GetMapping("boards/{boardId}/articles")
    public Slice<Article> listArticles(@PathVariable Long boardId, Pageable pageable) {
        return boardService.getBoard(boardId)
                .map(board -> articleListService.listByBoard(board, pageable))
                .orElse(new SliceImpl<>(Collections.emptyList()));
    }

    /**
     * GET /api/cafes/{nickname}/articles - list articles by cafe.
     * @param nickname nickname of cafe
     * @param pageable paging info
     * @return Slice of article list
     */
    @GetMapping("cafes/{nickname}/articles")
    public Slice<Article> listArticlesByCafe(@PathVariable String nickname, Pageable pageable) {
        return cafeService.getCafe(nickname)
                .map(cafe -> articleListService.listByCafe(cafe, pageable))
                .orElse(new SliceImpl<>(Collections.emptyList()));
    }
}
