package com.ssafy.enjoytrips.controller;

import com.ssafy.enjoytrips.model.dto.Comment;
import com.ssafy.enjoytrips.service.CommentService;
import com.ssafy.enjoytrips.service.QnaCommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna/comment")
public class QnaCommentController {

    private final QnaCommentServiceImpl qnaCommentService;

    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody Comment comment) {
        try {
            int result = qnaCommentService.register(comment);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @PutMapping("/{qna_comment_id}")
    public ResponseEntity<?> modify(@RequestBody Comment comment) {
        try {
            int result = qnaCommentService.modify(comment);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @DeleteMapping("/{qna_comment_id}")
    public ResponseEntity<?> delete(@PathVariable int qna_comment_id) {
        try {
            int result = qnaCommentService.delete(qna_comment_id);
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
