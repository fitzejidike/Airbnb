package com.example.airbnb.services;

import com.example.airbnb.dtos.request.MessageRequestDTO;
import com.example.airbnb.dtos.responses.MessageResponseDTO;

import java.util.List;

public interface MessageService {
    MessageResponseDTO sendMessage(MessageRequestDTO dto);
    List<MessageResponseDTO> getConversation(Long user1Id, Long user2Id);
}
