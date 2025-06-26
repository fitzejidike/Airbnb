package com.example.airbnb.services.implementation;

import com.example.airbnb.data.model.Property;
import com.example.airbnb.data.model.User;
import com.example.airbnb.data.repository.PropertyRepository;
import com.example.airbnb.data.repository.UserRepository;
import com.example.airbnb.dtos.request.PropertyRequestDTO;
import com.example.airbnb.dtos.responses.PropertyResponseDTO;
import com.example.airbnb.exception.UserNotFoundException;
import com.example.airbnb.services.PropertyService;
import com.example.airbnb.services.serviceUtils.PropertyMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    @Override
    public PropertyResponseDTO createProperty(PropertyRequestDTO dto, MultipartFile image) {
        User host = userRepository.findById(dto.getHostId())
                .orElseThrow(() -> new UserNotFoundException("Host not found"));

        String imageUrl = fileStorageService.saveImage(image); // Your implementation

        Property property = Property.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .location(dto.getLocation())
                .host(host)
                .imageUrl(imageUrl)
                .build();

        return PropertyMapper.toResponseDTO(propertyRepository.save(property));
    }

    @Override
    public PropertyResponseDTO getProperty(Long id) {
        return propertyRepository.findById(id)
                .map(PropertyMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Property not found"));
    }

    @Override
    public List<PropertyResponseDTO> getPropertiesByHost(Long hostId) {
        return propertyRepository.findByHostId(hostId)
                .stream()
                .map(PropertyMapper::toResponseDTO)
                .toList();
    }

    @Override
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}
