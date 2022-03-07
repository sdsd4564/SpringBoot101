INSERT INTO article(id, title, content) VALUES (1, 'Item1', 'Item-Content1');
INSERT INTO article(id, title, content) VALUES (2, 'Item2', 'Item-Content2');
INSERT INTO article(id, title, content) VALUES (3, 'Item3', 'Item-Content3');

-- article 더미 데이터 --
INSERT INTO comment(id, article_id, nickname, body) VALUES (1, 1, '닉네임1', '코멘트 1');
INSERT INTO comment(id, article_id, nickname, body) VALUES (2, 2, '닉네임2', '코멘트 2');
INSERT INTO comment(id, article_id, nickname, body) VALUES (3, 2, '닉네임3', '코멘트 3');
INSERT INTO comment(id, article_id, nickname, body) VALUES (4, 3, '닉네임1', '코멘트 19');
INSERT INTO comment(id, article_id, nickname, body) VALUES (5, 3, '닉네임3', '코멘트 128');
INSERT INTO comment(id, article_id, nickname, body) VALUES (6, 3, '닉네임4', '코멘트 311');