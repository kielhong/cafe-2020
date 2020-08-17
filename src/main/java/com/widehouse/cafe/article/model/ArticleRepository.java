package com.widehouse.cafe.article.model;

import com.widehouse.cafe.cafe.model.Cafe;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
    Slice<Article> findByBoard(Board board, Pageable pageable);

    @Query("SELECT a FROM Article a WHERE a.board.cafe = ?1")
    Slice<Article> findByCafe(Cafe cafe, Pageable pageable);
}
