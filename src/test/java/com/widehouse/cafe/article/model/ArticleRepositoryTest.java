package com.widehouse.cafe.article.model;

import static org.assertj.core.api.BDDAssertions.then;

import com.widehouse.cafe.cafe.model.Cafe;
import com.widehouse.cafe.cafe.model.CafeFixtures;
import com.widehouse.cafe.cafe.model.Category;
import java.time.ZonedDateTime;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
class ArticleRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ArticleRepository repository;

    private Cafe cafe;
    private Board board;

    @BeforeEach
    void setUp() {
        var category = entityManager.persist(Category.builder().name("category").build());
        cafe = entityManager.persist(Cafe.builder().category(category).name("foo").nickname("foo").description("cafe")
                .createdAt(ZonedDateTime.now()).build());
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

    @Test
    void findByCafe_then_listOfSameCafe() {
        // given
        var board1 = entityManager.persist(
                Board.builder().name("board1").description("desc1").order(1).cafe(cafe).build());
        var board2 = entityManager.persist(
                Board.builder().name("board2").description("desc2").order(2).cafe(cafe).build());
        IntStream.range(1, 7)
                .forEach(i -> entityManager.persist(
                        Article.builder().title("title" + i).content("content + i")
                                .board(i % 2 == 0 ? board1 : board2).createdAt(ZonedDateTime.now()).build()));
        // when
        var result = repository.findByCafe(cafe, PageRequest.of(0, 6));
        // then
        then(result.getContent()).satisfies(content -> {
            then(content).hasSize(6);
            then(content).extracting(article -> article.getBoard().getCafe()).containsOnly(cafe);
        });
    }
}