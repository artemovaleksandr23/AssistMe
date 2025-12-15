package com.otache.AssistMe.Controllers;

import com.otache.AssistMe.DTO.MessageDTO;
import com.otache.AssistMe.DTO.MessageResponseDTO;
import com.otache.AssistMe.Services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "*")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody MessageDTO dto) {
        messageService.sendMessage(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<MessageResponseDTO> getMessages() {
        return messageService.getAllMessages();
    }
}
