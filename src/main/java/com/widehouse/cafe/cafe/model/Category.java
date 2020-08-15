package com.widehouse.cafe.cafe.model;

import lombok.Builder;

public class Category {
    private String name;

    @Builder
    private Category(String name) {
        this.name = name;
    }
}
