package com.widehouse.cafe.cafe.service;

import com.widehouse.cafe.cafe.model.Cafe;
import com.widehouse.cafe.cafe.model.CafeRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CafeService {
    private CafeRepository cafeRepository;

    public CafeService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public Optional<Cafe> getCafe(String nickname) {
        return cafeRepository.findByNickname(nickname);
    }
}
