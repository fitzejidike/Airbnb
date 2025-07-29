package com.example.airbnb.services.implementation;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.airbnb.services.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    private final Cloudinary cloudinary;

    @Override
    public String saveImage(MultipartFile image) {
        try {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString(); // ✅ This is the public image URL
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }

//    private final Path root = Paths.get("uploads");
//
//    public FileStorageServiceImpl() {
//        try {
//            Files.createDirectories(root);
//        } catch (IOException e) {
//            throw new RuntimeException("Could not create uploads directory", e);
//        }
//    }
//
//    @Override
//    public String saveImage(MultipartFile image) {
//        try {
//            String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
//            Path filePath = root.resolve(filename);
//            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//            return "/uploads/" + filename;
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to save image", e);
//        }
//    }
}