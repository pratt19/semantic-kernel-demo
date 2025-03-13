package com.example.semantickerneldemo.integration;

import com.example.semantickerneldemo.model.ChatRequest;
import com.example.semantickerneldemo.model.ChatResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ChatIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String API_BASE_URL = "https://ai-proxy.lab.epam.com";

    @Test
    void chat_ValidRequest_ReturnsSuccess() throws Exception {
        // Arrange
        ChatRequest request = new ChatRequest();
        request.setInput("Hello, how are you?");

        // Act & Assert
        MvcResult result = mockMvc.perform(post("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.input").value(request.getInput()))
                .andExpect(jsonPath("$.response").exists())
                .andReturn();

        ChatResponse response = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            ChatResponse.class
        );

        assertNotNull(response);
        assertEquals(request.getInput(), response.getInput());
        assertNotNull(response.getResponse());
    }

    @Test
    void chat_InvalidRequest_ReturnsBadRequest() throws Exception {
        // Arrange
        ChatRequest request = new ChatRequest();
        // Empty input should trigger validation failure

        // Act & Assert
        mockMvc.perform(post("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void chat_InvalidJson_ReturnsBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content("invalid json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void chat_ServiceUnavailable_ReturnsError() throws Exception {
        // Arrange
        ChatRequest request = new ChatRequest();
        request.setInput("Test message");

        // Act & Assert
        mockMvc.perform(post("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is5xxServerError());
    }
} 