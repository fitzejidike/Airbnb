package com.example.airbnb.dtos.responses;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Getter
@Setter
@NoArgsConstructor
public class PropertyResponseDTO {
    private Long id;
    private String location;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private List<String> amenities;
    private String hostUsername;
    private boolean available;
}


