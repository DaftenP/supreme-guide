package com.ssafy.enjoytrips.controller;

import com.ssafy.enjoytrips.model.dto.Qna;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {

    private final QnaService qnaService;

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
    public ResponseEntity<?> update(@RequestBody Qna qna) {
        try {
            int result = qnaService.modify(qna);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 삭제
    @DeleteMapping("/{qna_id}")
    public ResponseEntity<?> delete(@PathVariable int qna_id) {
        try {
            int result = qnaService.delete(qna_id);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return exceptionHandling(e);
        }
    }

    // 작성
    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody Qna qna) {
        try {
            int result = qnaService.regist(qna);
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
