package com.example.airbnb.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String saveImage(MultipartFile image);
}