package com.widehouse.cafe.article.service;

import com.widehouse.cafe.article.model.Board;
import com.widehouse.cafe.article.model.BoardRepository;
import com.widehouse.cafe.cafe.model.Cafe;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * list boards of Cafe sorted.
     * @param cafe Cafe
     * @return list of Boards
     */
    public List<Board> listByCafe(Cafe cafe) {
        return boardRepository.findByCafe(cafe).stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public Optional<Board> getBoard(Long boardId) {
        return boardRepository.findById(boardId);
    }
}
