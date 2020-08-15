package com.widehouse.cafe.cafe.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.widehouse.cafe.cafe.model.CafeFixtures;
import com.widehouse.cafe.cafe.model.CafeRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CafeServiceTest {
    private CafeService service;

    @Mock
    private CafeRepository cafeRepository;

    @BeforeEach
    void setUp() {
        service = new CafeService(cafeRepository);
    }

    @Test
    void give_cafe_when_getCafe_then_returnOptionalCafe() {
        // given
        var cafe = CafeFixtures.foo();
        given(cafeRepository.findByNickname(anyString()))
                .willReturn(Optional.of(cafe));
        // when
        var result = service.getCafe("foo");
        // then
        then(result)
                .isPresent()
                .hasValue(cafe);
    }
}