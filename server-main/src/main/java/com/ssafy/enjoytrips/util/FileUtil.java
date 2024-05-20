package com.ssafy.enjoytrips.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {
    public String saveFile(MultipartFile file, String uploadPath) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File dest = new File(uploadPath, fileName);
        file.transferTo(dest);
        return fileName;
    }
}
