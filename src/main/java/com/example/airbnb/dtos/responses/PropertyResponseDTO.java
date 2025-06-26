package com.example.airbnb.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Builder
public class PropertyResponseDTO {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String location;
    private List<String> amenities;
    private String imageUrl;
    private Long hostId;
}

