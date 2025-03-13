package com.example.semantickerneldemo.controller;

import com.example.semantickerneldemo.mapper.ChatMapper;
import com.example.semantickerneldemo.model.ChatRequest;
import com.example.semantickerneldemo.model.ChatResponse;
import com.example.semantickerneldemo.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "Chat API", description = "API for chat functionality using Semantic Kernel")
public class ChatController {

    private final ChatService chatService;
    private final ChatMapper chatMapper;

    @PostMapping
    @Operation(summary = "Process chat request", description = "Generate a response based on the user's input")
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        String response = chatService.chat(request.getInput());
        return ResponseEntity.ok(chatMapper.toResponse(request, response));
    }
} 