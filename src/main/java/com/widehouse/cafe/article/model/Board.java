package com.widehouse.cafe.article.model;

import com.widehouse.cafe.cafe.model.Cafe;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Board implements Comparable<Board> {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    private Cafe cafe;
    @Column(name = "list_order")
    private int order;

    @Builder
    private Board(String name, String description, Cafe cafe, int order) {
        this.name = name;
        this.description = description;
        this.cafe = cafe;
        this.order = order;
    }

    @Override
    public int compareTo(Board board) {
        return this.getOrder() - board.getOrder();
    }
}
