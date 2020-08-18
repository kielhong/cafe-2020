package com.widehouse.cafe.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CafeNotFoundException extends RuntimeException {
    public CafeNotFoundException(String nickname) {
        super(nickname + " cafe does not exist");
    }
}
