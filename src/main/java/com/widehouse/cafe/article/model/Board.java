package com.widehouse.cafe.article.model;

import com.widehouse.cafe.cafe.model.Cafe;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Board {
    private String name;
    private String description;
    private Cafe cafe;

    @Builder
    private Board(String name, String description, Cafe cafe) {
        this.name = name;
        this.description = description;
        this.cafe = cafe;
    }
}
