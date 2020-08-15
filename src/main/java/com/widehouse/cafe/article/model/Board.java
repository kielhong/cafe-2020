package com.widehouse.cafe.article.model;

import com.widehouse.cafe.cafe.model.Cafe;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    private Cafe cafe;

    @Builder
    private Board(String name, String description, Cafe cafe) {
        this.name = name;
        this.description = description;
        this.cafe = cafe;
    }
}
