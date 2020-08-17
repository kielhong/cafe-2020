package com.widehouse.cafe.article.service;

import com.widehouse.cafe.article.model.Article;
import com.widehouse.cafe.article.model.ArticleRepository;
import com.widehouse.cafe.article.model.BoardRepository;
import com.widehouse.cafe.cafe.model.Cafe;
import java.util.Collections;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

@Service
public class ArticleListService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;

    public ArticleListService(ArticleRepository articleRepository, BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.boardRepository = boardRepository;
    }

    /**
     * list articles by board with paging.
     * @param boardId id of board
     * @param pageable paging info
     */
    public Slice<Article> listByBoard(Long boardId, Pageable pageable) {
        return boardRepository.findById(boardId)
                .map(board -> articleRepository.findByBoard(board, pageable))
                .orElse(new SliceImpl<>(Collections.emptyList()));
    }

    public Slice<Article> listByCafe(Cafe cafe, Pageable pageable) {
        return articleRepository.findByCafe(cafe, pageable);
    }
}
