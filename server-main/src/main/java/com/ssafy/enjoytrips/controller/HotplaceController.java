package com.ssafy.enjoytrips.controller;

import com.ssafy.enjoytrips.model.dto.Comment;
import com.ssafy.enjoytrips.model.dto.HotPlace;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.service.HotPlaceCommentServiceImpl;
import com.ssafy.enjoytrips.service.HotPlaceService;
import com.ssafy.enjoytrips.service.HotPlaceServiceImpl;
import com.ssafy.enjoytrips.util.FileUtil;
import com.ssafy.utils.CommentPurifier;
import com.ssafy.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/hotplace")
public class HotplaceController {

    private final HotPlaceService hotPlaceService;
    private final HotPlaceCommentServiceImpl hotPlaceCommentService;
    private final TokenProvider tokenProvider;
    private final FileUtil fileUtil;

    @Value("${com.ssafy.enjoytrips.upload.path}") // properties파일에 경로 설정
    private String uploadPath;

    private final CommentPurifier commentPurifier;


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
            log.debug("controller는 잘 됨");
            if (hotplace != null) {
                return ResponseEntity.ok(hotplace);
            } else {
                log.debug("안됨");
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    // 수정
    @PutMapping
    public ResponseEntity<?> modify(@RequestHeader("Authorization") String authorizationHeader, @RequestBody HotPlace hotPlace) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                // 헤더가 없거나 Bearer 토큰이 아닌 경우의 처리
                // 예: 401 Unauthorized 반환
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String token = authorizationHeader.substring(7);
            String userId = tokenProvider.getUserId(token);

            String fileName="";
            // 파일 저장
            if (hotPlace.getImage() != null && !hotPlace.getImage().isEmpty()) {
                fileName = fileUtil.saveFile(hotPlace.getImage(), uploadPath);
            }
            hotPlace.setImage(fileName);

            hotPlace.setWriter(userId);
            int result = hotPlaceService.modify(hotPlace);
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
            String token = authorizationHeader.substring(7);
            String userId = tokenProvider.getUserId(token);
            String fileName="";
            hotPlace.setWriter(userId);

            // 파일 저장
            if (hotPlace.getImage() != null && !hotPlace.getImage().isEmpty()) {
                fileName = fileUtil.saveFile(hotPlace.getImage(), uploadPath);
            }
            hotPlace.setImage(fileName);

            int result = hotPlaceService.regist(hotPlace);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }


    // --------- 댓글 ---------
    // 등록
    @PostMapping("/comment/regist")
    public ResponseEntity<?> registComment(@RequestHeader("Authorization") String authorizationHeader,
                                           @RequestBody Comment comment) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                // 헤더가 없거나 Bearer 토큰이 아닌 경우의 처리
                // 예: 401 Unauthorized 반환
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String token = authorizationHeader.substring(7);
            String userId = tokenProvider.getUserId(token);
            if (userId == null) {
                log.error("userId is null after token extraction.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to extract userId from token.");
            }
            comment.setUserId(userId);
            try {
                ResponseEntity<String> check = commentPurifier.check(comment);
                if (!"null".equals(check.getBody())) {
                    comment.setHarmful(true);
                }
            } catch (Exception e) {
                System.out.println("API 서버 통신 오류!!");
            }
            int result = hotPlaceCommentService.register(comment);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 댓글 수정
    @PutMapping("/comment/modify")
    public ResponseEntity<?> modifyComment(@RequestHeader("Authorization") String authorizationHeader,
                                           @RequestBody Comment comment) {
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
            try {
                ResponseEntity<String> check = commentPurifier.check(comment);
                if (!"null".equals(check.getBody())) {
                    comment.setHarmful(true);
                }
            } catch (Exception e) {
                System.out.println("API 서버 통신 오류!!");
            }
            int result = hotPlaceCommentService.modify(comment);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{hotplace_comment_id}")
    public ResponseEntity<?> deleteComment(@RequestHeader("Authorization") String authorizationHeader,
                                           @PathVariable int hotplace_comment_id) {
        try {
            int result = hotPlaceCommentService.delete(hotplace_comment_id);
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
