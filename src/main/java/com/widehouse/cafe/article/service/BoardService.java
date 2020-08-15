package com.widehouse.cafe.article.service;

import com.widehouse.cafe.article.model.Board;
import com.widehouse.cafe.cafe.model.Cafe;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    public List<Board> listByCafe(Cafe cafe) {
        return Collections.emptyList();
    }
}
