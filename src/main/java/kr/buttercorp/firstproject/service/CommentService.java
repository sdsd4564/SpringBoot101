package kr.buttercorp.firstproject.service;

import kr.buttercorp.firstproject.dto.CommentDto;
import kr.buttercorp.firstproject.entity.Article;
import kr.buttercorp.firstproject.entity.Comment;
import kr.buttercorp.firstproject.repository.ArticleRepository;
import kr.buttercorp.firstproject.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
        // 변환: 엔티티 -> DTO 및 반환
        return commentRepository.findByArticleId(articleId).stream().map(CommentDto::createCommentDto).collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        // 게시글 조회 및 예외 처리
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다!"));

        // 댓글 엔티티 생성
        Comment cmt = Comment.createComment(dto, article);

        // 댓글 엔티티 DB에 저장
        Comment created = commentRepository.save(cmt);

        // DTO로 변환 후 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다!"));

        // 댓글 수정
        target.patch(dto);

        // DB로 갱신
        Comment updated = commentRepository.save(target);

        // 댓글 엔티티 -> DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상 댓글이 없습니다!"));
        commentRepository.delete(target);

        return CommentDto.createCommentDto(target);
    }
}
