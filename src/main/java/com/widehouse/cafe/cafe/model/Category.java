package com.widehouse.cafe.cafe.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Category {
    private String name;

    @Builder
    private Category(String name) {
        this.name = name;
    }
}
