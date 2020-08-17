package com.widehouse.cafe.article.model;

import com.widehouse.cafe.cafe.model.Cafe;
import com.widehouse.cafe.cafe.model.CafeFixtures;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;

public class BoardFixtures {
    /**
     * Board list fixtures.
     * @param cafe Cafe
     */
    public static List<Board> boards(Cafe cafe) {
        return List.of(prepareBoard(1L, "board1", "board desc 1", 1, cafe),
                prepareBoard(2L, "board2", "board desc 2", 2, cafe),
                prepareBoard(3L, "board3", "board desc 3", 3, cafe));
    }

    public static Board board1(Cafe cafe) {
        return prepareBoard(1L, "board1", "board desc 1", 1, cafe);
    }

    public static Board board1() {
        return prepareBoard(1L, "board1", "board desc 1", 1, CafeFixtures.foo());
    }

    public static Board board2() {
        return prepareBoard(2L, "board2", "board desc 2", 2, CafeFixtures.foo());
    }

    private static Board prepareBoard(Long id, String name, String desc, int order, Cafe cafe) {
        var board = Board.builder().name(name).description(desc).order(order).cafe(cafe).build();
        ReflectionTestUtils.setField(board, "id", id);
        return board;
    }
}
