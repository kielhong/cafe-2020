package com.widehouse.cafe.article.service;

import com.widehouse.cafe.article.api.ArticleRequest;
import com.widehouse.cafe.article.model.Article;
import com.widehouse.cafe.article.model.ArticleRepository;
import com.widehouse.cafe.article.model.Board;
import com.widehouse.cafe.article.model.BoardRepository;
import com.widehouse.cafe.exception.ArticleNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;

    public ArticleService(ArticleRepository articleRepository, BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.boardRepository = boardRepository;
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
     */
    public void updateArticle(final UUID id, ArticleRequest articleRequest) {
        var article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
        var board = updateBoard(article.getBoard(), articleRequest.getBoardId());
        article.update(board, articleRequest.getTitle(), articleRequest.getContent());

        articleRepository.save(article);
    }

    private Board updateBoard(final Board srcBoard, final Long destBoardId) {
        if (srcBoard.getId().equals(destBoardId)) {
            return srcBoard;
        }

        var destBoardOptional = boardRepository.findById(destBoardId);
        if (destBoardOptional.isEmpty()) {
            throw new IllegalArgumentException(destBoardId + " board does not exits");
        }
        if (!srcBoard.getCafe().equals(destBoardOptional.get().getCafe())) {
            throw new IllegalArgumentException(destBoardId + " board is in different cafe");
        }
        return destBoardOptional.get();
    }
}
