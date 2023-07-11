package com.openclassrooms.rentals.service;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class DocumentStorageService {
    private final Path fileStorageLocation;
    private final String localStorage = "/Users/a240707/Temp/StorageRentalPics";
    private final String fileServerLocation = "http://localhost:8081/";

    public DocumentStorageService() throws IOException {
        this.fileStorageLocation = Paths.get(localStorage)
                .toAbsolutePath().normalize();
        Files.createDirectories(this.fileStorageLocation);
    }

    public String storeFile(MultipartFile file) throws IOException {
        // Normalize file name
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Copy file to the target location (Replacing existing file with the same name)
        Path targetLocation = this.fileStorageLocation.resolve(originalFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return fileServerLocation+originalFileName;
    }


}
