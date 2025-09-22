package com.example.airbnb.services.implementation;



import com.example.airbnb.data.model.Property;
import com.example.airbnb.data.model.User;
import com.example.airbnb.data.repository.PropertyRepository;
import com.example.airbnb.data.repository.UserRepository;
import com.example.airbnb.dtos.request.PropertyRequestDTO;
import com.example.airbnb.dtos.responses.PropertyResponseDTO;
import com.example.airbnb.services.FileStorageService;
import com.example.airbnb.services.serviceUtils.PropertyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PropertyServiceImplTest {

    @InjectMocks
    private PropertyServiceImpl propertyService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private PropertyMapper propertyMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProperty_Success() {
        // Arrange
        PropertyRequestDTO dto = new PropertyRequestDTO();
        dto.setLocation("Lagos");
        dto.setDescription("Nice place");
        dto.setPrice(BigDecimal.valueOf(100L));
        dto.setAvailable(true);

        MultipartFile image = mock(MultipartFile.class);
        String imageUrl = "http://cloudinary.com/test.jpg";

        User fakeHost = new User();
        fakeHost.setUsername("fitz");

        Property property = new Property();
        property.setHost(fakeHost);
        property.setLocation("Lagos");
        property.setDescription("Nice place");
        property.setPrice(BigDecimal.valueOf(100L));
        property.setAvailable(true);

        // Saved property returned by repository
        Property savedProperty = new Property();
        savedProperty.setId(1L);
        savedProperty.setHost(fakeHost);
        savedProperty.setLocation("Lagos");
        savedProperty.setDescription("Nice place");
        savedProperty.setPrice(BigDecimal.valueOf(100L));
        savedProperty.setAvailable(true);
        savedProperty.setImageUrl(imageUrl);

        PropertyResponseDTO responseDTO = PropertyResponseDTO.builder()
                .id(1L)
                .location("Lagos")
                .description("Nice place")
                .price(BigDecimal.valueOf(100L))
                .imageUrl(imageUrl)
                .amenities(List.of("WiFi", "AC"))
                .hostUsername("fitz")
                .available(true)
                .build();

        // Real mapper, so no mocking static call
        when(fileStorageService.saveImage(image)).thenReturn(imageUrl);
        when(userRepository.findById(any())).thenReturn(Optional.of(fakeHost));

        when(propertyRepository.save(any(Property.class))).thenReturn(savedProperty);

        // Act
        PropertyResponseDTO result = propertyService.createProperty(dto, image);

        // Assert
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getHostUsername()).isEqualTo("fitz");
        verify(propertyRepository).save(any(Property.class));
    }


    @Test
    void getProperty_WhenExists_ReturnsDTO() {
        User fakeHost = new User();
        fakeHost.setUsername("fitz");

        Property property = new Property();
        property.setId(1L);
        property.setHost(fakeHost);
        property.setLocation("Lagos");
        property.setDescription("Nice");
        property.setPrice(BigDecimal.valueOf(200));
        property.setImageUrl("img.jpg");
        property.setAvailable(true);

        PropertyResponseDTO expectedDTO = PropertyResponseDTO.builder()
                .id(1L)
                .location("Lagos")
                .description("Nice")
                .price(BigDecimal.valueOf(200))
                .imageUrl("img.jpg")
                .hostUsername("fitz")
                .available(true)
                .build();

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        PropertyResponseDTO result = propertyService.getProperty(1L);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedDTO);

    }

    @Test
    void getProperty_WhenMissing_ThrowsException() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> propertyService.getProperty(1L));
    }

    @Test
    void getPropertiesByHost_ReturnsList() {
        User host = new User();
        host.setId(1L);
        host.setUsername("fitz");

        Property property1 = new Property();
        property1.setId(1L);
        property1.setLocation("Lagos");
        property1.setPrice(BigDecimal.valueOf(200));
        property1.setDescription("Nice");
        property1.setImageUrl("img1.jpg");
        property1.setAvailable(true);
        property1.setHost(host); // ✅ Set host!

        Property property2 = new Property();
        property2.setId(2L);
        property2.setLocation("Abuja");
        property2.setPrice(BigDecimal.valueOf(250));
        property2.setDescription("Luxury");
        property2.setImageUrl("img2.jpg");
        property2.setAvailable(true);
        property2.setHost(host); // ✅ Set host!

        List<Property> mockProperties = List.of(property1, property2);

        when(propertyRepository.findByHostId(1L)).thenReturn(mockProperties);

        List<PropertyResponseDTO> result = propertyService.getPropertiesByHost(1L);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getHostUsername()).isEqualTo("fitz");
        assertThat(result.get(1).getLocation()).isEqualTo("Abuja");
    }

    @Test
    void deleteProperty_DeletesById() {
        Long id = 10L;

        propertyService.deleteProperty(id, );

        verify(propertyRepository).deleteById(id);
    }
}
