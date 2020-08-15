package com.widehouse.cafe.article.model;

import static org.assertj.core.api.BDDAssertions.then;

import com.widehouse.cafe.cafe.model.Cafe;
import com.widehouse.cafe.cafe.model.CafeFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
    private Cafe cafe;

    @BeforeEach
    void setUp() {
        cafe = CafeFixtures.foo();
    }

    @Test
    void when_build_then_constructed() {
        // when
        var board = Board.builder().name("board").description("desc").cafe(cafe).order(1).build();
        // then
        then(board)
                .hasFieldOrPropertyWithValue("name", "board")
                .hasFieldOrPropertyWithValue("description", "desc")
                .hasFieldOrPropertyWithValue("cafe", cafe)
                .hasFieldOrPropertyWithValue("order", 1);
    }

    @Test
    void compareTo() {
        var board1 = Board.builder().name("board1").cafe(cafe).order(1).build();
        var board2 = Board.builder().name("board2").cafe(cafe).order(2).build();
        // when
        var result = board1.compareTo(board2);
        // then
        then(result).isLessThan(0);
    }
}