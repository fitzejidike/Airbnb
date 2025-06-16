package com.example.airbnb.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentRequest {
    private int amount;
    private String email;

}
