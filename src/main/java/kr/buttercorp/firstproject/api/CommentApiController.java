package kr.buttercorp.firstproject.api;

import kr.buttercorp.firstproject.annotation.RunningTime;
import kr.buttercorp.firstproject.dto.CommentDto;
import kr.buttercorp.firstproject.service.CommentService;
import kr.buttercorp.firstproject.service.FileSystemStorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class CommentApiController {
    private final CommentService commentService;
    private final FileSystemStorageService fsService;

    // 댓글 목록 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {
        // 서비스에 위임
        List<CommentDto> dtos = commentService.comments(articleId);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }


    // 댓글 첨부파일 다운로드
    @GetMapping("/api/comments/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        CommentDto dto = commentService.comment(id);
        Resource file = fsService.loadAsResource(Paths.get("comments", id.toString(), dto.getAttached()));

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, MessageFormat.format("attachment; filename=\"{0}\"", file.getFilename()))
                .body(file);
    }

    // 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
//    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) {
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId,
                                             @RequestParam("nickname") String nickname,
                                             @RequestParam("body") String body,
                                             @RequestParam("article_id") Long article_id,
                                             @RequestParam("attached") MultipartFile attached
    ) {
        CommentDto dto = new CommentDto(null, article_id, nickname, body, attached.getOriginalFilename());
        CommentDto created = commentService.create(articleId, dto, attached);

        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    // 댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto) {
        CommentDto commentDto = commentService.update(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

    // 댓글 삭제
    @RunningTime
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id) {
        CommentDto commentDto = commentService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }
}
