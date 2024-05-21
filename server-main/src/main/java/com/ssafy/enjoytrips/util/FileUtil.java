package com.ssafy.enjoytrips.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Component
@Log4j2
public class FileUtil {
    public String saveFile(String base64Image, String uploadPath) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + ".png";
        File dest = new File(uploadPath, fileName);
        // 저장
        try (FileOutputStream fos = new FileOutputStream(dest)) {
            fos.write(decodedBytes);
        }
        return fileName;

    }
}
