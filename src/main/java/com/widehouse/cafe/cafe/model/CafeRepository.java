package com.widehouse.cafe.cafe.model;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    Optional<Cafe> findByNickname(String nickname);
}
