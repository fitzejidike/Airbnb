package com.example.airbnb.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferRequest {
    private int amount;
    private String recipientCode;
    private String reason;
}
