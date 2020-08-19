package com.widehouse.cafe.comment.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CommentFixtures {
    public static List<Comment> comments(UUID articleId, int size) {
        return IntStream.range(1, size + 1)
                .mapToObj(i -> Comment.builder()
                        .articleId(articleId).content("comment" + i).createdAt(ZonedDateTime.now()).build())
                .collect(Collectors.toList());
    }
}
