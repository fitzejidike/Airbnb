package com.example.airbnb.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentResponse {
    private boolean status;
    private String message;
    private PayStackData data;
}
