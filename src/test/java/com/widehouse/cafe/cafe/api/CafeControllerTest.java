package com.widehouse.cafe.cafe.api;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.widehouse.cafe.cafe.model.CafeFixtures;
import com.widehouse.cafe.cafe.service.CafeService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CafeController.class)
class CafeControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CafeService cafeService;

    @Nested
    @DisplayName("GET /api/cafe/{nickname}")
    class GetCafe {
        @Test
        @DisplayName("return cafe, if cafe exists")
        void given_cafe_when_get_by_nickname_then_returnCafe() throws Exception {
            // given
            var cafe = CafeFixtures.foo();
            given(cafeService.getCafe(anyString())).willReturn(Optional.of(cafe));
            // when
            mvc.perform(get("/api/cafes/{nickname}", "foo"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nickname").value(cafe.getNickname()));
        }

        @Test
        @DisplayName("404 not found, if cafe not exists")
        void given_notExistCafe__then_404NotFound() throws Exception {
            // given
            given(cafeService.getCafe(anyString())).willReturn(Optional.empty());
            // when
            mvc.perform(get("/api/cafes/{nickname}", "foo"))
                    .andExpect(status().isNotFound());
        }
    }

}