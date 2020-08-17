package com.widehouse.cafe.cafe.model;

import java.time.ZonedDateTime;

public class CafeFixtures {
    public static Cafe foo() {
        return Cafe.builder().name("foo").nickname("foo").description("desc").category(CategoryFixtures.life())
                .visible(true).createdAt(ZonedDateTime.now()).build();
    }

    public static Cafe bar() {
        return Cafe.builder().name("bar").nickname("bar").description("desc").category(CategoryFixtures.life())
                .visible(true).createdAt(ZonedDateTime.now()).build();
    }
}
