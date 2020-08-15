package com.widehouse.cafe.article.service;

import com.widehouse.cafe.article.model.Board;
import com.widehouse.cafe.article.model.BoardRepository;
import com.widehouse.cafe.cafe.model.Cafe;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> listByCafe(Cafe cafe) {
        return boardRepository.findByCafe(cafe);
    }
}
