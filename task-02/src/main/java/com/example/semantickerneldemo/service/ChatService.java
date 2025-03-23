package com.example.semantickerneldemo.service;

import com.example.semantickerneldemo.config.OpenAIConfig;
import com.example.semantickerneldemo.model.ChatRequest;
import com.example.semantickerneldemo.model.ChatResponse;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.chatcompletion.ChatMessage;
import com.microsoft.semantickernel.chatcompletion.ChatRole;
import com.microsoft.semantickernel.orchestration.SKContext;
import com.microsoft.semantickernel.semanticfunctions.KernelFunction;
import com.microsoft.semantickernel.semanticfunctions.KernelFunctionMetadata;
import com.microsoft.semantickernel.semanticfunctions.PromptTemplate;
import com.microsoft.semantickernel.semanticfunctions.PromptTemplateConfig;
import com.microsoft.semantickernel.textcompletion.CompletionSKFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ChatService {

    private final OpenAIConfig openAIConfig;
    private final Kernel kernel;
    private final ChatHistory chatHistory;

    @Autowired
    public ChatService(OpenAIConfig openAIConfig, Kernel kernel, ChatHistory chatHistory) {
        this.openAIConfig = openAIConfig;
        this.kernel = kernel;
        this.chatHistory = chatHistory;
    }

    public ChatResponse processChatRequest(ChatRequest request) {
        try {
            log.debug("Processing chat request: {}", request);
            
            // Add user message to chat history
            chatHistory.addMessage(new ChatMessage(ChatRole.USER, request.getMessage()));
            
            // Create a semantic function for chat
            String promptTemplate = """
                You are a helpful AI assistant. Please provide a clear and concise response to the following question.
                Consider the conversation history for context.
                
                Current question: {{$input}}
                """;
            
            PromptTemplateConfig promptConfig = new PromptTemplateConfig(promptTemplate);
            PromptTemplate prompt = new PromptTemplate(promptConfig);
            
            KernelFunctionMetadata metadata = KernelFunctionMetadata.builder()
                .name("chat")
                .description("A function to handle chat requests with conversation history")
                .build();
            
            CompletionSKFunction chatFunction = kernel.getSemanticFunctionBuilder()
                .withPromptTemplate(prompt)
                .withFunctionMetadata(metadata)
                .build();
            
            // Execute the function with chat history context
            CompletableFuture<SKContext> future = chatFunction.invokeAsync(request.getMessage());
            SKContext context = future.join();
            String response = context.getResult();
            
            // Add assistant's response to chat history
            chatHistory.addMessage(new ChatMessage(ChatRole.ASSISTANT, response));
            
            log.debug("Generated response: {}", response);
            return new ChatResponse(response);
            
        } catch (Exception e) {
            log.error("Error processing chat request", e);
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed to process chat request: " + e.getMessage()
            );
        }
    }

    public void clearChatHistory() {
        chatHistory.clear();
        // Re-add system message
        chatHistory.addMessage(new ChatMessage(ChatRole.SYSTEM, 
            "You are a helpful AI assistant with expertise in various domains. " +
            "Provide clear, accurate, and helpful responses while maintaining a professional tone."));
    }
} 