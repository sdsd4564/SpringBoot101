package kr.buttercorp.firstproject.repository;

import kr.buttercorp.firstproject.entity.Article;
import kr.buttercorp.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest // JPA 연동 테스트
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글 모든 댓글 조회")
    void findByArticleId() {
        /* Case 1: 3번 게시글의 모든 댓글 조회*/
        {
            // 입력 데이터 준비
            Long articleId = 3L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상
            // 'Item3', 'Item-Content3');
            Article article = new Article(3L, "Item3", "Item-Content3");
            Comment c1 = new Comment(4L, article, "닉네임1", "코멘트 19");
            Comment c2 = new Comment(5L, article, "닉네임3", "코멘트 128");
            Comment c3 = new Comment(6L, article, "닉네임4", "코멘트 311");
            List<Comment> expected = Arrays.asList(c1, c2, c3);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "3번글의 모든 댓글 출력");
        }

        /* Case 2: 1번 게시글의 모든 댓글 조회*/
        {
            // 입력 데이터 준비
            Long articleId = 1L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상
            // 'Item3', 'Item-Content3');
            Article article = new Article(1L, "Item1", "Item-Content1");
            //INSERT INTO comment(id, article_id, nickname, body) VALUES (1, 1, '닉네임1', '코멘트 1');
            Comment c1 = new Comment(1L, article, "닉네임1", "코멘트 1");
            List<Comment> expected = Collections.singletonList(c1);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "1번글의 모든 댓글 출력");
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        /* Case 1: 닉네임1의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "닉네임1";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상
            Article article1 = new Article(1L, "Item1", "Item-Content1");
            Article article3 = new Article(3L, "Item3", "Item-Content3");
            Comment c1 = new Comment(1L, article1, "닉네임1", "코멘트 1");
            Comment c3 = new Comment(4L, article3, "닉네임1", "코멘트 19");
            List<Comment> expected = Arrays.asList(c1, c3);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "닉네임1의 모든 댓글 출력");
        }
    }
}