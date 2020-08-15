package com.widehouse.cafe.article.api;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;

class ArticleRequestTest {
    @Test
    void construct() {
        // when
        var result = new ArticleRequest(1L, "title", "content");
        // then
        then(result)
                .hasFieldOrPropertyWithValue("boardId", 1L)
                .hasFieldOrPropertyWithValue("title", "title")
                .hasFieldOrPropertyWithValue("content", "content");
    }
}