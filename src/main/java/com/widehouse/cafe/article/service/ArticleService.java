package com.widehouse.cafe.article.service;

import com.widehouse.cafe.article.model.Article;
import com.widehouse.cafe.article.model.Board;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    public List<Article> listByBoard(Board board) {
        return Collections.emptyList();
    }
}
