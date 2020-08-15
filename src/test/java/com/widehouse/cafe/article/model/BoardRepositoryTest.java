package com.widehouse.cafe.article.model;

import static org.assertj.core.api.BDDAssertions.then;

import com.widehouse.cafe.cafe.model.Cafe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class BoardRepositoryTest {
    @Autowired
    private BoardRepository repository;
    @Autowired
    private TestEntityManager entityManager;

    private Cafe cafe;

    @BeforeEach
    void setUp() {
        cafe = entityManager.persist(Cafe.builder().build());
    }

    @Test
    void when_listByCafe_then_returnList() {
        // given
        var boards = BoardFixtures.boards(cafe);
        boards.stream()
                .forEach(board -> entityManager.persist(board));
        // when
        var result = repository.findByCafe(cafe);
        // then
        then(result).isEqualTo(boards);
    }
}