package com.example.semantickerneldemo.config;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.SKBuilders;
import com.microsoft.semantickernel.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.chatcompletion.ChatMessage;
import com.microsoft.semantickernel.chatcompletion.ChatRole;
import com.microsoft.semantickernel.semanticfunctions.PromptExecutionSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SemanticKernelConfig {

    @Value("${client-openai.key}")
    private String apiKey;

    @Value("${client-openai.endpoint}")
    private String endpoint;

    @Value("${client-openai.deployment-name}")
    private String deploymentName;

    @Value("${client-openai.key-id}")
    private String keyId;

    @Bean
    public PromptExecutionSettings promptExecutionSettings() {
        return PromptExecutionSettings.builder()
                .temperature(0.7f)  // Higher temperature for more creative responses
                .maxTokens(2000)    // Maximum tokens in response
                .topP(0.95f)        // Nucleus sampling parameter
                .frequencyPenalty(0.0f)  // No frequency penalty
                .presencePenalty(0.0f)   // No presence penalty
                .build();
    }

    @Bean
    public ChatHistory chatHistory() {
        ChatHistory history = new ChatHistory();
        // Add system message to set context
        history.addMessage(new ChatMessage(ChatRole.SYSTEM, 
            "You are a helpful AI assistant with expertise in various domains. " +
            "Provide clear, accurate, and helpful responses while maintaining a professional tone."));
        return history;
    }

    @Bean
    public ChatCompletionService chatCompletionService(PromptExecutionSettings settings) {
        return SKBuilders.chatCompletion()
                .withOpenAIChatCompletionService(
                    deploymentName,
                    apiKey,
                    endpoint,
                    keyId
                )
                .withExecutionSettings(settings)
                .build();
    }

    @Bean
    public Kernel kernel(ChatCompletionService chatCompletionService) {
        return SKBuilders.kernel()
                .withChatCompletionService(chatCompletionService)
                .build();
    }
} 