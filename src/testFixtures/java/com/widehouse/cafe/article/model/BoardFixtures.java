package com.widehouse.cafe.article.model;

import com.widehouse.cafe.cafe.model.Cafe;
import com.widehouse.cafe.cafe.model.CafeFixtures;
import java.util.List;

public class BoardFixtures {
    /**
     * Board list fixtures.
     * @param cafe
     */
    public static List<Board> boards(Cafe cafe) {
        return List.of(prepareBoard("board1", "board desc 1", 1, cafe),
                prepareBoard("board2", "board desc 2", 2, cafe),
                prepareBoard("board3", "board desc 3", 3, cafe));
    }

    public static Board board1(Cafe cafe) {
        return prepareBoard("board1", "board desc 1", 1, cafe);
    }

    public static Board board1() {
        return prepareBoard("board1", "board desc 1", 1, CafeFixtures.foo());
    }

    private static Board prepareBoard(String name, String desc, int order, Cafe cafe) {
        return Board.builder().name(name).description(desc).order(order).cafe(cafe).build();
    }
}
