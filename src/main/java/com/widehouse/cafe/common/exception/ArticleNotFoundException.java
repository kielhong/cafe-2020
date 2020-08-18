package com.widehouse.cafe.common.exception;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(String id) {
        super(id + " Article does not exist");
    }

    public ArticleNotFoundException(UUID id) {
        this(id.toString());
    }
}
