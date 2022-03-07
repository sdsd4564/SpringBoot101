package kr.buttercorp.firstproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.buttercorp.firstproject.entity.Article;
import kr.buttercorp.firstproject.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDto {
    private Long id;
    @JsonProperty("article_id")
    private Long articleId;
    private String nickname;
    private String body;

    public static CommentDto createCommentDto(Comment cmt) {
        return new CommentDto(
                cmt.getId(),
                cmt.getArticle().getId(),
                cmt.getNickname(),
                cmt.getBody()
        );
    }
}
