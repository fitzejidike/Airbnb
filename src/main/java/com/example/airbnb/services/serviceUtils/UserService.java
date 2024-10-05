package com.example.airbnb.services.serviceUtils;

import com.example.airbnb.dtos.request.RegisterUserRequest;
import com.example.airbnb.dtos.responses.RegisterUserResponse;

public interface UserService {
 RegisterUserResponse register(RegisterUserRequest request);


}
