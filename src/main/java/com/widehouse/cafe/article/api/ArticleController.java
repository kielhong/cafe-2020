package com.widehouse.cafe.article.api;

import com.widehouse.cafe.article.model.Article;
import com.widehouse.cafe.article.service.ArticleService;
import com.widehouse.cafe.article.service.BoardService;
import com.widehouse.cafe.exception.ArticleNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ArticleController {
    private BoardService boardService;
    private ArticleService articleService;

    public ArticleController(BoardService boardService, ArticleService articleService) {
        this.boardService = boardService;
        this.articleService = articleService;
    }

    /**
     * GET /api/cafes/{nickname}/boards/{boardId}/articles.
     * @param nickname nickname of Cafe
     * @param boardId id of Board
     * @return list of Articles
     */
    @GetMapping("cafes/{nickname}/boards/{boardId}/articles")
    public List<Article> listArticles(@PathVariable String nickname, @PathVariable Long boardId) {
        return boardService.getBoard(boardId)
                .map(board -> articleService.listByBoard(board))
                .orElse(Collections.emptyList());
    }

    @GetMapping("articles/{id}")
    public Article getArticle(@PathVariable String id) {
        return articleService.getArticle(UUID.fromString(id))
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

}
