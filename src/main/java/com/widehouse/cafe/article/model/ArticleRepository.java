package com.widehouse.cafe.article.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, String> {
    List<Article> findByBoard(Board board);
}
