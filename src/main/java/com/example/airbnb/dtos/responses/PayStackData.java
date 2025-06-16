package com.example.airbnb.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PayStackData {
    private String authorizationUrl;
    private String accessCode;
    private String reference;
    private String transferCode;
}
