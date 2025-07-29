package com.example.airbnb.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageRequestDTO {
    private Long senderId;
    private Long receiverId;
    private String content;
}
