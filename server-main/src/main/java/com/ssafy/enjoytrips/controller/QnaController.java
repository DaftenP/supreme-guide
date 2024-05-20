package com.ssafy.enjoytrips.controller;

import com.ssafy.enjoytrips.model.dto.Comment;
import com.ssafy.enjoytrips.model.dto.Qna;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.service.QnaCommentServiceImpl;
import com.ssafy.enjoytrips.service.QnaService;
import com.ssafy.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/qna")
public class QnaController {

    private final QnaService qnaService;
    private final QnaCommentServiceImpl qnaCommentService;
    private final TokenProvider tokenProvider;

    // 전체 조회
    @GetMapping("/all")
    public ResponseEntity<?> list(SearchCondition condition) {
        try {
            List<Qna> qna = qnaService.list(condition);
            return ResponseEntity.ok(qna);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    // 개별 조회
    @GetMapping("/view/{qna_id}")
    public ResponseEntity<?> select(@PathVariable int qna_id) {
        try {
            Qna qna = qnaService.select(qna_id);
            qnaService.updateView(qna_id);
            if (qna != null) {
                return ResponseEntity.ok(qna);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // 수정
    @PutMapping("/{qna_id}")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String authorizationHeader, @PathVariable int qna_id, @RequestBody Qna qna) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                // 헤더가 없거나 Bearer 토큰이 아닌 경우의 처리
                // 예: 401 Unauthorized 반환
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }

            String token = authorizationHeader.substring(7);
            String userId = tokenProvider.getUserId(token);

            if (!qna.getQnaWriter().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정할 수 있는 권한이 없습니다.");
            }
            qna.setQnaWriter(userId);

            int result = qnaService.modify(qna);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 삭제
    @DeleteMapping("/{qna_id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String authorizationHeader, @PathVariable int qna_id) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            // 헤더가 없거나 Bearer 토큰이 아닌 경우의 처리
            // 예: 401 Unauthorized 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String token = authorizationHeader.substring(7);
            String userId = tokenProvider.getUserId(token);

            Qna qna = qnaService.select(qna_id);
            log.debug(qna.getQnaId());
            if (!qna.getQnaWriter().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("작성자만 글 삭제를 할 수 있습니다.");
            }
            int result = qnaService.delete(qna_id);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return exceptionHandling(e);
        }
    }

    // 작성
    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Qna qna) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                // 헤더가 없거나 Bearer 토큰이 아닌 경우의 처리
                // 예: 401 Unauthorized 반환
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String token = authorizationHeader.substring(7);
            String userId = tokenProvider.getUserId(token);
            qna.setQnaWriter(userId);
            int result = qnaService.regist(qna);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // ------댓글-------

    // 등록
    @PostMapping("/comment/regist")
    public ResponseEntity<?> registComment(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Comment comment) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                // 헤더가 없거나 Bearer 토큰이 아닌 경우의 처리
                // 예: 401 Unauthorized 반환
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }

            String token = authorizationHeader.substring(7);
            String userId = tokenProvider.getUserId(token);
            log.debug("Extracted userId from token: " + userId);

            System.out.println("UserID!!!!!!!!!!!!"+userId);
            if (userId == null) {
                log.error("userId is null after token extraction.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to extract userId from token.");
            }

            comment.setUserId(userId);
            log.debug("Comment after setting userId: " + comment);

            int result = qnaCommentService.register(comment);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 댓글 수정
    @PutMapping("/comment/modify")
    public ResponseEntity<?> modifyComment(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Comment comment) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String token = authorizationHeader.substring(7);
            String userId = tokenProvider.getUserId(token);

            if (!comment.getUserId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정할 수 있는 권한이 없습니다.");
            }
            comment.setUserId(userId);
            int result = qnaCommentService.modify(comment);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{qna_comment_id}")
    public ResponseEntity<?> deleteComment(@RequestHeader("Authorization") String authorizationHeader, @PathVariable int qna_comment_id) {
        try {
//            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
//            }
//            String token = authorizationHeader.substring(7);
//            String userId = tokenProvider.getUserId(token);
//
//            Comment comment = qnaCommentService.selectCommentWriter(qna_comment_id);
//            if (comment == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글을 찾을 수 없습니다.");
//            }
//
//            // 댓글 작성자와 현재 사용자 비교
//            if (!comment.getUserId().equals(userId)) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제할 수 있는 권한이 없습니다.");
//            }

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
