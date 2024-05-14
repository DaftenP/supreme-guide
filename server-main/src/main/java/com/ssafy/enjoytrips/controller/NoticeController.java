package com.ssafy.enjoytrips.controller;

import com.ssafy.enjoytrips.model.dto.Notice;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    // 전체 조회
    @GetMapping("/all")
    public ResponseEntity<?> list(SearchCondition searchCondition) {
        try {
            List<Notice> list = noticeService.list(searchCondition);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
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
            return ResponseEntity.internalServerError().build();
        }
    }

    // 글 작성
    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody Notice notice) {
        try {
            int result = noticeService.regist(notice);
            return ResponseEntity.ok(result);
        } catch(Exception e) {
            return exceptionHandling(e);
        }
    }

    // 글 수정
    @PutMapping("/{notice_id}")
    public ResponseEntity<?> modify(@RequestBody Notice notice) {
        try {
            int result = noticeService.modify(notice);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return exceptionHandling(e);
        }
    }


    // 글 삭제
    @DeleteMapping("/{notice_id}")
    public ResponseEntity<?> delete(@PathVariable int notice_id) {
        try {
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
