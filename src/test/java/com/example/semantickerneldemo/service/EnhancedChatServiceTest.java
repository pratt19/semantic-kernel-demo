package com.example.semantickerneldemo.service;

import com.example.semantickerneldemo.config.OpenAIConfig;
import com.example.semantickerneldemo.model.ChatRequest;
import com.example.semantickerneldemo.model.ChatResponse;
import com.microsoft.semantickernel.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.chatcompletion.ChatMessage;
import com.microsoft.semantickernel.chatcompletion.ChatRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Enhanced Chat Service Tests")
public class EnhancedChatServiceTest {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatHistory chatHistory;

    @BeforeEach
    void setUp() {
        chatService.clearChatHistory();
    }

    @Test
    @DisplayName("Test Chat History Management")
    void testChatHistoryManagement() {
        // Send first message
        ChatRequest request1 = new ChatRequest("What is the capital of France?");
        ChatResponse response1 = chatService.processChatRequest(request1);
        
        // Verify chat history
        assertEquals(3, chatHistory.getMessages().size()); // System + User + Assistant
        assertEquals(ChatRole.SYSTEM, chatHistory.getMessages().get(0).getRole());
        assertEquals(ChatRole.USER, chatHistory.getMessages().get(1).getRole());
        assertEquals(ChatRole.ASSISTANT, chatHistory.getMessages().get(2).getRole());
        
        // Send follow-up question
        ChatRequest request2 = new ChatRequest("Tell me more about its history.");
        ChatResponse response2 = chatService.processChatRequest(request2);
        
        // Verify chat history grew
        assertEquals(5, chatHistory.getMessages().size()); // Added User + Assistant
    }

    @Test
    @DisplayName("Test Temperature Impact")
    void testTemperatureImpact() {
        // Send same question twice to see temperature impact
        ChatRequest request = new ChatRequest("What is the meaning of life?");
        ChatResponse response1 = chatService.processChatRequest(request);
        ChatResponse response2 = chatService.processChatRequest(request);
        
        // Responses should be different due to temperature
        assertNotEquals(response1.getResponse(), response2.getResponse());
    }

    @Test
    @DisplayName("Test Context Retention")
    void testContextRetention() {
        // Send initial question
        ChatRequest request1 = new ChatRequest("My name is John.");
        chatService.processChatRequest(request1);
        
        // Send follow-up using context
        ChatRequest request2 = new ChatRequest("What is my name?");
        ChatResponse response = chatService.processChatRequest(request2);
        
        // Response should include the name from context
        assertTrue(response.getResponse().toLowerCase().contains("john"));
    }

    @Test
    @DisplayName("Test Error Handling")
    void testErrorHandling() {
        // Test empty message
        ChatRequest emptyRequest = new ChatRequest("");
        assertThrows(IllegalArgumentException.class, () -> {
            chatService.processChatRequest(emptyRequest);
        });
        
        // Verify chat history wasn't affected
        assertEquals(1, chatHistory.getMessages().size()); // Only system message
    }

    @Test
    @DisplayName("Test Chat History Clear")
    void testChatHistoryClear() {
        // Add some messages
        chatService.processChatRequest(new ChatRequest("Hello"));
        chatService.processChatRequest(new ChatRequest("How are you?"));
        
        // Clear history
        chatService.clearChatHistory();
        
        // Verify only system message remains
        assertEquals(1, chatHistory.getMessages().size());
        assertEquals(ChatRole.SYSTEM, chatHistory.getMessages().get(0).getRole());
    }

    @Test
    @DisplayName("Test Long Conversation")
    void testLongConversation() {
        // Send multiple messages to test context window
        String[] messages = {
            "What is Java?",
            "Tell me about its features.",
            "How does it compare to Python?",
            "What are its main use cases?"
        };
        
        for (String message : messages) {
            ChatResponse response = chatService.processChatRequest(new ChatRequest(message));
            assertNotNull(response);
            assertNotNull(response.getResponse());
        }
        
        // Verify chat history grew properly
        assertEquals(messages.length * 2 + 1, chatHistory.getMessages().size()); // System + (User + Assistant) * messages
    }
} 