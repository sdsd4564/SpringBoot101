package kr.buttercorp.firstproject.api;

import kr.buttercorp.firstproject.annotation.RunningTime;
import kr.buttercorp.firstproject.dto.CommentDto;
import kr.buttercorp.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@Slf4j
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    // 댓글 목록 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {
        // 서비스에 위임
        List<CommentDto> dtos = commentService.comments(articleId);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) {
        CommentDto commentDto = commentService.create(articleId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

    @PostMapping("/api/comments/{id}/upload")
    public ResponseEntity<String> upload(@PathVariable Long id, MultipartFile file) throws IOException {
        log.info("comments id: {}", id);
        if (!file.isEmpty()) {
            File newFile = new File(file.getOriginalFilename());
            log.info(newFile.toString());
            log.info(newFile.getAbsolutePath());
            log.info(newFile.toURI().toString());
//            file.transferTo(newFile);
            file.transferTo(Paths.get("comments", id.toString(), file.getOriginalFilename()).toFile());
        }

        return ResponseEntity.status(HttpStatus.OK).build();
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
