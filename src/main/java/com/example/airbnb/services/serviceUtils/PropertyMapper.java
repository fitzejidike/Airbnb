package com.example.airbnb.services.serviceUtils;

import com.example.airbnb.data.model.Property;
import com.example.airbnb.dtos.responses.PropertyResponseDTO;

public class PropertyMapper {
    public static PropertyResponseDTO toResponseDTO(Property property) {
        return PropertyResponseDTO.builder()
                .id(property.getId())
                .title(property.getTitle())
                .description(property.getDescription())
                .price(property.getPrice())
                .location(property.getLocation())
                .imageUrl(property.getImageUrl())
                .hostId(property.getHost().getId())
                .build();
    }
}

