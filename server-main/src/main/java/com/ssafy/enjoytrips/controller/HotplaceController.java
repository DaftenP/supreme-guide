package com.ssafy.enjoytrips.controller;

import com.ssafy.enjoytrips.model.dto.HotPlace;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.service.HotPlaceService;
import com.ssafy.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotplace")
public class HotplaceController {

    private final HotPlaceService hotPlaceService;
    private final TokenProvider tokenProvider;

    // 전체 조회
    @GetMapping("/all")
    public ResponseEntity<?> list(SearchCondition searchCondition) {
        try {
            List<HotPlace> list = hotPlaceService.list();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    // 개별 조회
    @GetMapping("/view/{hotplace_id}")
    public ResponseEntity<?> select(@PathVariable int hotplace_id) {
        try {
            HotPlace hotplace = hotPlaceService.select(hotplace_id);
            if (hotplace != null) {
                return ResponseEntity.ok(hotplace);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    // 수정

    // 삭제

    // 등록

    // exception handling
    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .internalServerError()
                .body("Sorry: " + e.getMessage());
    }
}
