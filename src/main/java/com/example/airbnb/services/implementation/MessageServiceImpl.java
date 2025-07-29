package com.example.airbnb.services.implementation;

import com.example.airbnb.data.model.Message;
import com.example.airbnb.data.model.User;
import com.example.airbnb.data.repository.MessageRepository;
import com.example.airbnb.dtos.request.MessageRequestDTO;
import com.example.airbnb.dtos.responses.MessageResponseDTO;
import com.example.airbnb.services.EmailService;
import com.example.airbnb.services.MessageService;
import com.example.airbnb.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final EmailService emailService;

    private final UserService userService;
    @Value("${email.enabled:true}") // allows toggling in profiles
    private boolean emailEnabled;

    @Override
    public MessageResponseDTO sendMessage(MessageRequestDTO dto) {
        // 1. Fetch sender and receiver from userService
        User sender = userService.getUserById(dto.getSenderId());
        User receiver = userService.getUserById(dto.getReceiverId());

        // 2. Build and save the message
        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(dto.getContent())
                .timestamp(LocalDateTime.now())
                .build();

        messageRepository.save(message);

        // 3. Send email notification (optional via property)
        if (emailEnabled) {
            String subject = "📩 New message from " + sender.getUsername();
            String body = "Hi " + receiver.getUsername() + ",\n\n"
                    + "You have a new message:\n\n"
                    + dto.getContent() + "\n\n"
                    + "Log in to respond.";
            emailService.sendEmail(receiver.getEmail(), subject, body);
        }

        // 4. Return structured response
        return MessageResponseDTO.builder()
                .id(message.getId())
                .senderUsername(sender.getUsername())
                .receiverUsername(receiver.getUsername())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .build();
    }

    @Override
    public List<MessageResponseDTO> getConversation(Long user1Id, Long user2Id) {
        // Find messages between user1 ↔ user2
        List<Message> messages = messageRepository
                .findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(user1Id, user2Id, user1Id, user2Id);

        // Map to DTOs
        return messages.stream().map(msg -> MessageResponseDTO.builder()
                .id(msg.getId())
                .senderUsername(msg.getSender().getUsername())
                .receiverUsername(msg.getReceiver().getUsername())
                .content(msg.getContent())
                .timestamp(msg.getTimestamp())
                .build()).toList();
    }
}
