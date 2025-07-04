package com.example.airbnb.services.serviceUtils;

import com.example.airbnb.data.model.Property;
import com.example.airbnb.dtos.request.PropertyRequestDTO;
import com.example.airbnb.dtos.responses.PropertyResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PropertyMapper {
    public static Property fromDTO(PropertyRequestDTO dto) {
        Property property = new Property();
        property.setLocation(dto.getLocation());
        property.setDescription(dto.getDescription());
        property.setPrice(dto.getPrice());
        property.setAvailable(dto.getAvailable());
        // imageUrl should be set after Cloudinary upload
        return property;
    }

    public static PropertyResponseDTO toResponseDTO(Property property) {
        return PropertyResponseDTO.builder()
                .id(property.getId())
                .location(property.getLocation())
                .description(property.getDescription())
                .price(property.getPrice())
                .available(property.getAvailable())
                .imageUrl(property.getImageUrl())
                .hostUsername(property.getHost().getUsername())
                .build();
    }

    public static List<PropertyResponseDTO> toResponseDTOList(List<Property> properties) {
        return properties.stream()
                .map(PropertyMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}



