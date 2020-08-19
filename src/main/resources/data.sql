-- category
INSERT INTO category (id, name) VALUES (1, 'life');

-- cafe
INSERT INTO cafe (id, name, nickname, description, category_id, created_at, visible) values
    (1, 'foo', 'foo', 'foo-desc', 1, now(), true);

-- board
INSERT INTO board(id, name, description, cafe_id, list_order) values
    (1, 'board1', 'board1', 1, 1),
    (2, 'board2', 'board2', 1, 2);

-- article
INSERT INTO article(id, title, board_id, created_at) values
    ('f6019a601fbd43eab2c3a4b558cc5b51', '게시물1', 1, now()),
    ('89525dda97064dd2891d82d9d2f32565', '게시물2', 1, now()),
    ('b9a262b41b204355a35367885f21b264', '게시물3', 1, now());
INSERT INTO article_content(article_id, body) values
    ('f6019a601fbd43eab2c3a4b558cc5b51', '게시물 내용1'),
    ('89525dda97064dd2891d82d9d2f32565', '게시물 내용2'),
    ('b9a262b41b204355a35367885f21b264', '게시물 내용3');