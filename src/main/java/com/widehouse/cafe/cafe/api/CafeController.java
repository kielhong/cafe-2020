package com.widehouse.cafe.cafe.api;

import com.widehouse.cafe.cafe.model.Cafe;
import com.widehouse.cafe.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cafe")
public class CafeController {
    private final CafeService cafeService;

    public CafeController(CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @GetMapping("{nickname}")
    public Cafe getCafe(@PathVariable String nickname) {
        return cafeService.getCafe(nickname);
    }
}
