package com.widehouse.cafe.article.api;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ArticleRequest {
    private Long boardId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
