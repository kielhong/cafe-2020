package com.widehouse.cafe.article.model;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
    List<Article> findByBoard(Board board);
}
