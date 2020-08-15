package com.widehouse.cafe.cafe.model;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;

class CategoryTest {
    @Test
    void builder() {
        var category = Category.builder().name("name").build();
        // then
        then(category)
                .hasFieldOrPropertyWithValue("name", "name");
    }
}
