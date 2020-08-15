package com.widehouse.cafe.article.service;

import com.widehouse.cafe.article.api.ArticleRequest;
import com.widehouse.cafe.article.model.Article;
import com.widehouse.cafe.article.model.ArticleRepository;
import com.widehouse.cafe.article.model.Board;
import com.widehouse.cafe.article.model.BoardRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> listByBoard(Board board) {
        return articleRepository.findByBoard(board);
    }

    public Optional<Article> getArticle(UUID id) {
        return articleRepository.findById(id);
    }

    public Article createArticle(Board board, ArticleRequest articleRequest) {
        return articleRepository.save(Article.from(board, articleRequest));
    }
}
