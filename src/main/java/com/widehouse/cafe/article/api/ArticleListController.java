package com.widehouse.cafe.article.api;

import com.widehouse.cafe.article.model.Article;
import com.widehouse.cafe.article.service.ArticleListService;
import com.widehouse.cafe.cafe.service.CafeService;
import java.util.Collections;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ArticleListController {
    private final ArticleListService articleListService;
    private final CafeService cafeService;

    public ArticleListController(ArticleListService articleListService, CafeService cafeService) {
        this.articleListService = articleListService;
        this.cafeService = cafeService;
    }

    @GetMapping(value = "articles", params = "boardId")
    public Slice<Article> listArticles(@RequestParam Long boardId, Pageable pageable) {
        return articleListService.listByBoard(boardId, pageable);
    }

    /**
     * GET /api/cafes/{nickname}/articles?page=1&size=10&sort=createdAt,desc.
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
