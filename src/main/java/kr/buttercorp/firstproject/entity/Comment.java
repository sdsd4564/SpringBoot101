package kr.buttercorp.firstproject.entity;

import kr.buttercorp.firstproject.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;

    @Column
    private String attached;

    public static Comment createComment(CommentDto dto, Article article) {
        if (dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 ID는 자동으로 생성됨!");
        if (dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 ID가 서로 다름!");

        return new Comment(dto.getId(), article, dto.getNickname(), dto.getBody(), dto.getAttached());
    }

    public void patch(CommentDto dto) {
        // 예외 발생
        if (id != dto.getId())
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다");

        // 객체 갱신
        if (dto.getNickname() != null)
            nickname = dto.getNickname();
        if (dto.getBody() != null)
            body = dto.getBody();
        if (dto.getAttached() != null)
            attached = dto.getAttached();
    }
}
