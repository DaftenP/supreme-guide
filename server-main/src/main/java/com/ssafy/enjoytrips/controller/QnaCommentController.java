package com.ssafy.enjoytrips.controller;

import com.ssafy.enjoytrips.model.dto.Qna;
import com.ssafy.enjoytrips.model.dto.QnaComment;
import com.ssafy.enjoytrips.service.CommentService;
import com.ssafy.enjoytrips.service.QnaService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna/comment")
public class QnaCommentController {

    private final CommentService commentService;

    @GetMapping("/all")
    public ResponseEntity<?> list(int qna_id) {
        try {
            List<QnaComment> qnaCommentList = commentService.list(qna_id);
            return ResponseEntity.ok(qnaCommentList);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody QnaComment comment) {
        try {
            int result = commentService.register(comment);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @PutMapping("/{qna_comment_id}")
    public ResponseEntity<?> modify(@RequestBody QnaComment comment) {
        try {
            int result = commentService.modify(comment);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @DeleteMapping("/{qna_comment_id}")
    public ResponseEntity<?> delete(@PathVariable int qna_comment_id) {
        try {
            int result = commentService.delete(qna_comment_id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .internalServerError()
                .body("Sorry: " + e.getMessage());
    }
}
