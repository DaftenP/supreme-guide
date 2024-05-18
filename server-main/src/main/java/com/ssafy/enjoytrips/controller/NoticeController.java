package com.ssafy.enjoytrips.controller;

import com.ssafy.enjoytrips.model.dto.Notice;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.service.NoticeService;
import com.ssafy.utils.TokenProvider;
import com.sun.net.httpserver.HttpsServer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final TokenProvider tokenProvider;

    // 전체 조회
    @GetMapping("/all")
    public ResponseEntity<?> list(SearchCondition searchCondition) {
        try {
            List<Notice> list = noticeService.list(searchCondition);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    // 개별 조회
    @GetMapping("/view/{notice_id}")
    public ResponseEntity<?> select(@PathVariable int notice_id) {
        try {
            Notice notice = noticeService.select(notice_id);
            if (notice != null) {
                return ResponseEntity.ok(notice);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // 글 작성
    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Notice notice) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                // 헤더가 없거나 Bearer 토큰이 아닌 경우의 처리
                // 예: 401 Unauthorized 반환
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String token = authorizationHeader.substring(7);
            String userId = tokenProvider.getUserId(token);
            notice.setNoticeWriter(userId);

            int result = noticeService.regist(notice);
            return ResponseEntity.ok(result);
        } catch(Exception e) {
            return exceptionHandling(e);
        }
    }

    // 글 수정
    @PutMapping("/{notice_id}")
    public ResponseEntity<?> modify(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Notice notice) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                // 헤더가 없거나 Bearer 토큰이 아닌 경우의 처리
                // 예: 401 Unauthorized 반환
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String token = authorizationHeader.substring(7);
            String userId = tokenProvider.getUserId(token);

            if (!notice.getNoticeWriter().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정할 권한이 없습니다.");
            }

            notice.setNoticeWriter(userId);

            int result = noticeService.modify(notice);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return exceptionHandling(e);
        }
    }


    // 글 삭제
    @DeleteMapping("/{notice_id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String authorizationHeader, @PathVariable int notice_id) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                // 헤더가 없거나 Bearer 토큰이 아닌 경우의 처리
                // 예: 401 Unauthorized 반환
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String token = authorizationHeader.substring(7);
            String userId = tokenProvider.getUserId(token);

            Notice notice = noticeService.select(notice_id);
            if (!notice.getNoticeWriter().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("작성자만 수정이 가능합니다.");
            }
            int result = noticeService.delete(notice_id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }


    // exception handling
    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .internalServerError()
                .body("Sorry: " + e.getMessage());
    }

}
