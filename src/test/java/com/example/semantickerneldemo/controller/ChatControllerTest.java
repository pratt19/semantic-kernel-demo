package com.example.semantickerneldemo.controller;

import com.example.semantickerneldemo.mapper.ChatMapper;
import com.example.semantickerneldemo.model.ChatRequest;
import com.example.semantickerneldemo.model.ChatResponse;
import com.example.semantickerneldemo.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatControllerTest {

    @Mock
    private ChatService chatService;

    @Mock
    private ChatMapper chatMapper;

    private ChatController chatController;

    @BeforeEach
    void setUp() {
        chatController = new ChatController(chatService, chatMapper);
    }

    @Test
    void chat_Success() {
        // Arrange
        ChatRequest request = new ChatRequest();
        request.setInput("Hello");
        String serviceResponse = "Hi there!";
        ChatResponse expectedResponse = new ChatResponse();
        expectedResponse.setInput("Hello");
        expectedResponse.setResponse(serviceResponse);

        when(chatService.chat(anyString())).thenReturn(serviceResponse);
        when(chatMapper.toResponse(any(ChatRequest.class), anyString())).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ChatResponse> result = chatController.chat(request);

        // Assert
        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(expectedResponse, result.getBody());
        verify(chatService).chat(request.getInput());
        verify(chatMapper).toResponse(request, serviceResponse);
    }

    @Test
    void chat_ValidationFailure() {
        // Arrange
        ChatRequest request = new ChatRequest();
        // Empty input should trigger validation failure

        // Act & Assert
        assertThrows(Exception.class, () -> chatController.chat(request));
        verifyNoInteractions(chatService);
        verifyNoInteractions(chatMapper);
    }
} 