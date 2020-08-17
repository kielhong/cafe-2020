package com.widehouse.cafe.article.api;

import com.widehouse.cafe.article.model.Article;
import com.widehouse.cafe.article.service.ArticleService;
import com.widehouse.cafe.article.service.BoardService;
import com.widehouse.cafe.exception.ArticleNotFoundException;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api")
public class ArticleController {
    private BoardService boardService;
    private ArticleService articleService;

    public ArticleController(BoardService boardService, ArticleService articleService) {
        this.boardService = boardService;
        this.articleService = articleService;
    }

    @GetMapping("articles/{id}")
    public Article getArticle(@PathVariable UUID id) {
        return articleService.getArticle(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    /**
     * POST /api/articles.
     * @param articleRequest request for article
     * @return id of created article
     */
    @PostMapping("articles")
    public Map<String, UUID> createArticle(@Valid @RequestBody ArticleRequest articleRequest) {
        var board = boardService.getBoard(articleRequest.getBoardId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        articleRequest.getBoardId() + " board does not exist"));

        var article = articleService.createArticle(board, articleRequest);
        return Map.of("id", article.getId());
    }

    /**
     * PUT /api/articles/{id}.
     * @param id id of article
     * @param articleRequest requestBody for article
     */
    @PutMapping("articles/{id}")
    public Map<String, UUID> updateArticle(@PathVariable UUID id,
                                           @RequestBody ArticleRequest articleRequest) {
        try {
            articleService.updateArticle(id, articleRequest);
            return Map.of("id", id);
        } catch (IllegalArgumentException iae) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, iae.getMessage());
        }
    }

    /**
     * DELETE /api/articles/{id}.
     * @param id id of article
     * @return id of deleted article
     */
    @DeleteMapping("articles/{id}")
    public Map<String, UUID> deleteArticle(@PathVariable UUID id) {
        articleService.deleteArticle(id);
        return Map.of("id", id);
    }
}