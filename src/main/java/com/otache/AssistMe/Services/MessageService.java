package com.otache.AssistMe.Services;

import com.otache.AssistMe.DTO.MessageDTO;
import com.otache.AssistMe.DTO.MessageResponseDTO;
import com.otache.AssistMe.Models.Message.Message;
import com.otache.AssistMe.Models.User.User;
import com.otache.AssistMe.Repositories.MessageRepository;
import org.springframework.stereotype.Service;
import com.otache.AssistMe.Repositories.UserRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public int sendMessage(MessageDTO dto) {
        try {
            Message message = new Message(
                    0,
                    dto.getUserId(),
                    dto.getTitle(),
                    dto.getBody(),
                    dto.getStatus(),
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            return messageRepository.save(message);
        } catch (SQLException e) {
            throw new RuntimeException("Error sending message", e);
        }
    }

    public List<MessageResponseDTO> getAllMessages() {
        try {
            List<Message> messages = messageRepository.findAll();
            return messages.stream()
                    .map(msg -> {
                        Optional<User> userOpt = null;
                        try {
                            userOpt = userRepository.findById(msg.getUserId());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        String username = userOpt.map(User::getName).orElse("Unknown");

                        // Форматируем даты
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                        String createdAt = msg.getCreatedAt().format(formatter);
                        String updatedAt = msg.getUpdatedAt().format(formatter);

                        return new MessageResponseDTO(
                                msg.getId(),
                                username,
                                msg.getTitle(),
                                msg.getBody(),
                                msg.getStatus(),
                                msg.getCreatedAt(),
                                msg.getUpdatedAt()
                        );
                    })
                    .toList();
        } catch (SQLException e) {
            throw new RuntimeException("Error loading messages", e);
        }
    }

}
