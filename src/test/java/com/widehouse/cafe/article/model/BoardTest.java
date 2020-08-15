package com.widehouse.cafe.article.model;

import static org.assertj.core.api.BDDAssertions.then;

import com.widehouse.cafe.cafe.model.CafeFixtures;
import org.junit.jupiter.api.Test;

class BoardTest {
    @Test
    void when_build_then_constructed() {
        // when
        var cafe = CafeFixtures.foo();
        var board = Board.builder().name("board").description("desc").cafe(cafe).build();
        // then
        then(board)
                .hasFieldOrPropertyWithValue("name", "board")
                .hasFieldOrPropertyWithValue("description", "desc")
                .hasFieldOrPropertyWithValue("cafe", cafe);
    }
}