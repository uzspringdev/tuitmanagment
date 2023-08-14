package com.example.demo.utility;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class UploadUtility {
    public String uploadPhoto(MultipartFile photo, String photoUrl) {
        final String UPLOAD_DIR = "./uploads/";
        final String DOWNLOAD_DIR = "../../uploads/";
        final String FILE_EXTENSION_PNG = ".png";
        final String FILE_EXTENSION_JPG = ".jpg";
        String url = null;
        Path path;
        String fileName = "";
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()));
        if (photo.isEmpty()) {
            return photoUrl;
        }
        if (originalFileName.endsWith(FILE_EXTENSION_JPG)) {
            fileName = UUID.randomUUID() + FILE_EXTENSION_JPG;
        } else if (originalFileName.endsWith(FILE_EXTENSION_PNG)) {
            fileName = UUID.randomUUID() + FILE_EXTENSION_PNG;
        }
        try {
            path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(photo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            url = DOWNLOAD_DIR + fileName;
            //Files.deleteIfExists(Paths.get(photoUrl.replace("../.","")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
}
