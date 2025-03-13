package com.example.semantickerneldemo.service;

import com.example.semantickerneldemo.config.OpenAIConfig;
import com.example.semantickerneldemo.model.ChatRequest;
import com.example.semantickerneldemo.model.ChatResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Chat Service Experiments")
public class ChatServiceExperimentsTest {

    @Autowired
    private ChatService chatService;

    @Autowired
    private OpenAIConfig openAIConfig;

    @BeforeEach
    void setUp() {
        assertNotNull(chatService, "ChatService should be autowired");
        assertNotNull(openAIConfig, "OpenAIConfig should be autowired");
    }

    @Test
    @DisplayName("Test Simple Question Response")
    void testSimpleQuestion() {
        ChatRequest request = new ChatRequest("What is the capital of France?");
        ChatResponse response = chatService.processChatRequest(request);
        
        assertNotNull(response);
        assertNotNull(response.getResponse());
        assertTrue(response.getResponse().contains("Paris"));
    }

    @Test
    @DisplayName("Test Detailed Question Response")
    void testDetailedQuestion() {
        ChatRequest request = new ChatRequest("Explain the history of Paris in detail.");
        ChatResponse response = chatService.processChatRequest(request);
        
        assertNotNull(response);
        assertNotNull(response.getResponse());
        assertTrue(response.getResponse().length() > 100); // Should be detailed
    }

    @Test
    @DisplayName("Test Creative Question Response")
    void testCreativeQuestion() {
        ChatRequest request = new ChatRequest("If Paris could talk, what would it say about its history?");
        ChatResponse response = chatService.processChatRequest(request);
        
        assertNotNull(response);
        assertNotNull(response.getResponse());
        assertTrue(response.getResponse().length() > 50);
    }

    @Test
    @DisplayName("Test Code Generation")
    void testCodeGeneration() {
        ChatRequest request = new ChatRequest("Write a Java function to calculate factorial.");
        ChatResponse response = chatService.processChatRequest(request);
        
        assertNotNull(response);
        assertNotNull(response.getResponse());
        assertTrue(response.getResponse().contains("public static"));
        assertTrue(response.getResponse().contains("factorial"));
    }

    @Test
    @DisplayName("Test Technical Explanation")
    void testTechnicalExplanation() {
        ChatRequest request = new ChatRequest("Explain how a binary search tree works.");
        ChatResponse response = chatService.processChatRequest(request);
        
        assertNotNull(response);
        assertNotNull(response.getResponse());
        assertTrue(response.getResponse().contains("node"));
        assertTrue(response.getResponse().contains("tree"));
    }

    @Test
    @DisplayName("Test Response Time")
    void testResponseTime() {
        ChatRequest request = new ChatRequest("What is 2+2?");
        long startTime = System.currentTimeMillis();
        
        ChatResponse response = chatService.processChatRequest(request);
        
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        
        assertNotNull(response);
        assertTrue(responseTime < 5000); // Should respond within 5 seconds
    }

    @Test
    @DisplayName("Test Error Handling")
    void testErrorHandling() {
        ChatRequest request = new ChatRequest(""); // Empty message should trigger validation
        assertThrows(IllegalArgumentException.class, () -> {
            chatService.processChatRequest(request);
        });
    }

    @Test
    @DisplayName("Test Long Context Handling")
    void testLongContext() {
        String longText = "The quick brown fox jumps over the lazy dog. ".repeat(50);
        ChatRequest request = new ChatRequest("Summarize: " + longText);
        ChatResponse response = chatService.processChatRequest(request);
        
        assertNotNull(response);
        assertNotNull(response.getResponse());
        assertTrue(response.getResponse().length() < longText.length());
    }
} 