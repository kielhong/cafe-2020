package com.widehouse.cafe.article.model;

import static org.assertj.core.api.BDDAssertions.then;

import com.widehouse.cafe.cafe.model.CafeFixtures;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class ArticleRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ArticleRepository repository;

    private Board board;

    @BeforeEach
    void setUp() {
        board = entityManager.persist(Board.builder()
                .name("board").description("desc").order(1).cafe(CafeFixtures.foo()).build());
    }

    @Test
    void save_then_contentToArticleContent() {
        // given
        var article = Article.builder()
                .title("title").content("content").board(board).createdAt(ZonedDateTime.now()).build();
        repository.save(article);
        // when
        var result = repository.findById(article.getId());
        // then
        then(result)
                .isPresent()
                .hasValue(article);
    }
}