package com.widehouse.cafe.article.model;

import com.widehouse.cafe.cafe.model.Cafe;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByCafe(Cafe cafe);
}
