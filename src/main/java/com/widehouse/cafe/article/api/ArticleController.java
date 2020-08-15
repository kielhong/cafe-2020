package com.widehouse.cafe.article.api;

import com.widehouse.cafe.article.model.Article;
import com.widehouse.cafe.article.service.ArticleService;
import com.widehouse.cafe.article.service.BoardService;
import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cafes/{nickname}")
public class ArticleController {
    private BoardService boardService;
    private ArticleService articleService;

    public ArticleController(BoardService boardService, ArticleService articleService) {
        this.boardService = boardService;
        this.articleService = articleService;
    }

    @GetMapping("boards/{boardId}/articles")
    public List<Article> listArticles(@PathVariable String nickname, @PathVariable Long boardId) {
        return boardService.getBoard(boardId)
                .map(board -> articleService.listByBoard(board))
                .orElse(Collections.emptyList());
    }
}
