package com.filestorage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {



        private final String uploadDir = "uploads/";

        public String saveFile(MultipartFile file, Long formationId) throws IOException {
            String formationFolder = uploadDir + "formation_" + formationId;
            File directory = new File(formationFolder);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = formationFolder + "/" + file.getOriginalFilename();
            Path path = Paths.get(filePath);
            Files.write(path, file.getBytes());

            return filePath;
        }


}
