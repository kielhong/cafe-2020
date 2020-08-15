package com.widehouse.cafe.cafe.service;

import com.widehouse.cafe.cafe.model.Cafe;
import org.springframework.stereotype.Service;

@Service
public class CafeService {
    public Cafe getCafe(String nickname) {
        return Cafe.builder().build();
    }
}
