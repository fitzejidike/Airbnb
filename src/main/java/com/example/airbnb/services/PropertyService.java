package com.example.airbnb.services;

import com.example.airbnb.dtos.request.PropertyRequestDTO;
import com.example.airbnb.dtos.responses.PropertyResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PropertyService {
    PropertyResponseDTO createProperty(PropertyRequestDTO dto, MultipartFile image);
    PropertyResponseDTO getProperty(Long id);
    List<PropertyResponseDTO> getPropertiesByHost(Long hostId);
    void deleteProperty(Long propertyId, String currentUsername);
}

