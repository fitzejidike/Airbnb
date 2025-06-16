package com.example.airbnb.services;

import com.example.airbnb.dtos.request.RegisterPropertyRequest;
import com.example.airbnb.dtos.responses.RegisterPropertyResponse;

public interface PropertyService {
    RegisterPropertyRequest register(RegisterPropertyResponse registerPropertyResponse);

}
