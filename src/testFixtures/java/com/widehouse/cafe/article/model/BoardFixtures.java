package com.widehouse.cafe.article.model;

import com.widehouse.cafe.cafe.model.Cafe;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.test.util.ReflectionTestUtils;

public class BoardFixtures {
    private static final Board board1 = Board.builder().name("board1").description("board desc 1").build();
    private static final Board board2 = Board.builder().name("board2").description("board desc 2").build();
    private static final Board board3 = Board.builder().name("board3").description("board desc 3").build();

    public static List<Board> boards(Cafe cafe) {
        return Arrays.asList(board1, board2, board3).stream()
                .map(board -> {
                    ReflectionTestUtils.setField(board, "cafe", cafe);
                    return board;
                })
                .collect(Collectors.toList());
    }

    public static Board board1(Cafe cafe) {
        ReflectionTestUtils.setField(board1, "cafe", cafe);
        return board1;
    }
}
