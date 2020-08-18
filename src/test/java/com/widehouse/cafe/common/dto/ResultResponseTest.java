package com.widehouse.cafe.common.dto;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class ResultResponseTest {
    @Test
    void generic_string() {
        // when
        var result = new ResultResponse<>("12345");
        // then
        then(result.getId()).isEqualTo("12345");
    }

    @Test
    void generic_uuid() {
        // given
        var uuid = UUID.randomUUID();
        // when
        var result = new ResultResponse<>(uuid);
        // then
        then(result.getId()).isEqualTo(uuid);
    }
}