package com.ssafy.enjoytrips.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotPlace {
    private int hotplaceId;
    private String hotplaceName;
    private double latitude;
    private double longitude;
    private String addr1;
    private String addr2;
    private String comment;
    private int category;
    private String image;
    private String writer;
    private MultipartFile imageFile; // 파일 업로드를 위한 필드 추가
}

//hotplace_id int PK
//hotplace_name varchar(255)
//hotplace_lng decimal(20,17)
//hotplace_lat decimal(20,17)
//hotplace_addr1 varchar(100)
//hotplace_addr2 varchar(50)
//hotplace_comment text
//hotplace_category int
//hotplace_image text
//hotplace_writer varchar(20)
