package com.example.airbnb.Controller;

import com.example.airbnb.dtos.request.PropertyRequestDTO;
import com.example.airbnb.dtos.responses.ApiResponse;
import com.example.airbnb.dtos.responses.PropertyResponseDTO;
import com.example.airbnb.services.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<PropertyResponseDTO>> createProperty(
            @RequestPart("property") @Valid PropertyRequestDTO dto,
            @RequestPart("image") MultipartFile image) {

        var response = propertyService.createProperty(dto, image);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Property created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PropertyResponseDTO>> getProperty(@PathVariable Long id) {
        var response = propertyService.getProperty(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Property retrieved successfully"));
    }

    @GetMapping("/host/{hostId}")
    public ResponseEntity<ApiResponse<List<PropertyResponseDTO>>> getPropertiesByHost(@PathVariable Long hostId) {
        var response = propertyService.getPropertiesByHost(hostId);
        return ResponseEntity.ok(ApiResponse.success(response, "Properties for host retrieved successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProperty(@PathVariable Long id, Principal principal) {
        propertyService.deleteProperty(id, principal.getName()); // ownership enforced
        return ResponseEntity.ok(ApiResponse.success("Deleted successfully", "Property deleted successfully"));
    }

}

