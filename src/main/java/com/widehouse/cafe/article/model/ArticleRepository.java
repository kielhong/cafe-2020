package com.widehouse.cafe.article.model;

import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
    Slice<Article> findByBoard(Board board, Pageable pageable);
}
