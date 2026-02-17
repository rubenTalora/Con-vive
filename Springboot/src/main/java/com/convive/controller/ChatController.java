
package com.convive.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.time.LocalDateTime;
import com.convive.entity.ChatMessage;
import com.convive.repository.ChatMessageRepository;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageRepository repository;

    @GetMapping("/")
    public List<ChatMessage> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}/messages")
    public List<ChatMessage> getMessages(@PathVariable Long id) {
        return repository.findByChatId(id);
    }

    @PostMapping("/send")
    public ChatMessage send(@RequestBody ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());
        return repository.save(message);
    }
}
