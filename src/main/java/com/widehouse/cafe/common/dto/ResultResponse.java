package com.widehouse.cafe.common.dto;

import lombok.Getter;

@Getter
public class ResultResponse<T> {
    private T id;

    public ResultResponse(T id) {
        this.id = id;
    }
}
