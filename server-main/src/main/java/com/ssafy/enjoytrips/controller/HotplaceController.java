package com.ssafy.enjoytrips.controller;

import com.ssafy.enjoytrips.model.dto.HotPlace;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.service.HotPlaceService;
import com.ssafy.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            List<HotPlace> list = hotPlaceService.list(searchCondition);
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
    @PutMapping
    public ResponseEntity<?> modify(@RequestHeader("Authoriation") String authorizationHeader, @RequestBody HotPlace hotplace) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                // 헤더가 없거나 Bearer 토큰이 아닌 경우의 처리
                // 예: 401 Unauthorized 반환
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String token = authorizationHeader.substring(7);
            String userId = tokenProvider.getUserId(token);

            if (!hotplace.getWriter().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정할 권한이 없습니다.");
            }
            int result = hotPlaceService.modify(hotplace);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 삭제
    @DeleteMapping("/{hotplace_id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String authorizationHeader, @PathVariable int hotplace_id) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                // 헤더가 없거나 Bearer 토큰이 아닌 경우의 처리
                // 예: 401 Unauthorized 반환
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String token = authorizationHeader.substring(7);
            String userId = tokenProvider.getUserId(token);

            HotPlace hotplace = hotPlaceService.select(hotplace_id);
            if (!hotplace.getWriter().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("작성자만 삭제가 가능합니다.");
            }
            int result = hotPlaceService.delete(hotplace_id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 등록
    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestHeader("Authorization") String authorizationHeader, @RequestBody HotPlace hotPlace) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                // 헤더가 없거나 Bearer 토큰이 아닌 경우의 처리
                // 예: 401 Unauthorized 반환
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }

            int result = hotPlaceService.regist(hotPlace);
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
