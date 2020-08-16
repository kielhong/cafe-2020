package com.widehouse.cafe.article.service;

import com.widehouse.cafe.article.api.ArticleRequest;
import com.widehouse.cafe.article.model.Article;
import com.widehouse.cafe.article.model.ArticleRepository;
import com.widehouse.cafe.article.model.Board;
import com.widehouse.cafe.article.model.BoardRepository;
import com.widehouse.cafe.exception.ArticleNotFoundException;
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

    /**
     * delete a article.
     * @param id id of article
     */
    public void deleteArticle(UUID id) {
        var article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));

        articleRepository.delete(article);
    }

    /**
     * update a article with request.
     * @param id id of article to update
     * @param articleRequest update request
     * @return updated article
     */
    public Article updateArticle(UUID id, ArticleRequest articleRequest) {
        var article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
        article.update(articleRequest);
        return articleRepository.save(article);
    }
}
