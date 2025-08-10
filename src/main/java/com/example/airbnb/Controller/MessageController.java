package com.example.airbnb.Controller;

import com.example.airbnb.dtos.request.MessageRequestDTO;
import com.example.airbnb.dtos.responses.ApiResponse;
import com.example.airbnb.services.MessageService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    @PostMapping
    public ResponseEntity<ApiResponse<?>> sendMessage(@RequestBody MessageRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(messageService.sendMessage(dto), "Message sent"));
    }

    @GetMapping("/conversation")
    public ResponseEntity<ApiResponse<?>> getConversation(
            @RequestParam Long user1Id, @RequestParam Long user2Id) {

        return ResponseEntity.ok(ApiResponse.success(
                messageService.getConversation(user1Id, user2Id), "Conversation loaded"));
    }


}
