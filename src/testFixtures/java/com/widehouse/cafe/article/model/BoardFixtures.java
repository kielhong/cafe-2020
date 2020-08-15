package com.widehouse.cafe.article.model;

import com.widehouse.cafe.cafe.model.Cafe;
import java.util.List;

public class BoardFixtures {
    public static List<Board> boards(Cafe cafe) {
        return List.of(prepareBoard("board1", "board desc 1", 1, cafe),
                prepareBoard("board2", "board desc 2", 2, cafe),
                prepareBoard("board3", "board desc 3", 3, cafe));
    }

    public static Board board1(Cafe cafe) {
        return prepareBoard("board1", "board desc 1", 1, cafe);
    }

    private static Board prepareBoard(String name, String desc, int order, Cafe cafe) {
        return Board.builder().name(name).description(desc).order(order).cafe(cafe).build();
    }
}
