package com.example.airbnb.data.repository;

import com.example.airbnb.data.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property,Long> {
}
