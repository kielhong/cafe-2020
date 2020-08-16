package com.widehouse.cafe.article.model;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;

class ArticleContentTest {
    @Test
    void updateBody_then_update_body() {
        // given
        var content = new ArticleContent(ArticleFixtures.article(), "content");
        // when
        content.updateBody("new body");
        // then
        then(content.getBody()).isEqualTo("new body");
    }
}