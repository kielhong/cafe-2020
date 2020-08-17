package com.widehouse.cafe.article.service;

import com.widehouse.cafe.article.model.Article;
import com.widehouse.cafe.article.model.ArticleRepository;
import com.widehouse.cafe.article.model.Board;
import com.widehouse.cafe.cafe.model.Cafe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class ArticleListService {
    private final ArticleRepository articleRepository;

    public ArticleListService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * list articles by board with paging.
     * @param pageable paging info
     */
    public Slice<Article> listByBoard(Board board, Pageable pageable) {
        return articleRepository.findByBoard(board, pageable);
    }

    public Slice<Article> listByCafe(Cafe cafe, Pageable pageable) {
        return articleRepository.findByCafe(cafe, pageable);
    }
}
