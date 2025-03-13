package com.example.semantickerneldemo.service;

import com.example.semantickerneldemo.exception.ChatServiceException;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.semanticfunctions.KernelFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private Kernel kernel;

    @Mock
    private KernelFunction chatFunction;

    private ChatService chatService;

    @BeforeEach
    void setUp() {
        chatService = new ChatService(kernel);
    }

    @Test
    void chat_Success() {
        // Arrange
        String input = "Hello, how are you?";
        String expectedResponse = "I'm doing well, thank you!";
        when(kernel.getFunction("chat")).thenReturn(chatFunction);
        when(chatFunction.invoke(anyString())).thenReturn(expectedResponse);

        // Act
        String result = chatService.chat(input);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(kernel).getFunction("chat");
        verify(chatFunction).invoke(input);
    }

    @Test
    void chat_ThrowsException_WhenKernelFunctionNotFound() {
        // Arrange
        String input = "Hello";
        when(kernel.getFunction("chat")).thenReturn(null);

        // Act & Assert
        assertThrows(ChatServiceException.class, () -> chatService.chat(input));
        verify(kernel).getFunction("chat");
        verifyNoInteractions(chatFunction);
    }

    @Test
    void chat_ThrowsException_WhenInvocationFails() {
        // Arrange
        String input = "Hello";
        when(kernel.getFunction("chat")).thenReturn(chatFunction);
        when(chatFunction.invoke(anyString())).thenThrow(new RuntimeException("Test exception"));

        // Act & Assert
        ChatServiceException exception = assertThrows(ChatServiceException.class, () -> chatService.chat(input));
        assertEquals("Failed to process chat request", exception.getMessage());
        verify(kernel).getFunction("chat");
        verify(chatFunction).invoke(input);
    }
} 