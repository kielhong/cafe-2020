package com.widehouse.cafe.comment.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentRequest {
    private String articleId;
    private String content;
}
