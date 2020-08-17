package com.widehouse.cafe.article.api;

import com.widehouse.cafe.article.model.Article;
import com.widehouse.cafe.article.service.ArticleListService;
import com.widehouse.cafe.article.service.ArticleService;
import com.widehouse.cafe.article.service.BoardService;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/articles")
public class ArticleListController {
    private final ArticleListService articleListService;

    public ArticleListController(ArticleListService articleListService) {
        this.articleListService = articleListService;
    }

    @GetMapping(params = "boardId")
    public Slice<Article> listArticles(@RequestParam Long boardId, Pageable pageable) {
        return articleListService.listByBoard(boardId, pageable);
    }
}
