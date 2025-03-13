package com.example.semantickerneldemo.service;

import com.example.semantickerneldemo.config.OpenAIConfig;
import com.example.semantickerneldemo.model.ChatRequest;
import com.example.semantickerneldemo.model.ChatResponse;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.SKBuilders;
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

@Slf4j
@Service
public class ChatService {

    private final OpenAIConfig openAIConfig;
    private final Kernel kernel;

    @Autowired
    public ChatService(OpenAIConfig openAIConfig) {
        this.openAIConfig = openAIConfig;
        this.kernel = SKBuilders.kernel()
                .withOpenAIChatCompletionService(
                    openAIConfig.getDeploymentName(),
                    openAIConfig.getKey(),
                    openAIConfig.getEndpoint(),
                    openAIConfig.getKeyId()
                )
                .build();
    }

    public ChatResponse processChatRequest(ChatRequest request) {
        try {
            log.debug("Processing chat request: {}", request);
            
            // Create a semantic function for chat
            String promptTemplate = """
                You are a helpful AI assistant. Please provide a clear and concise response to the following question:
                {{$input}}
                """;
            
            PromptTemplateConfig promptConfig = new PromptTemplateConfig(promptTemplate);
            PromptTemplate prompt = new PromptTemplate(promptConfig);
            
            KernelFunctionMetadata metadata = KernelFunctionMetadata.builder()
                .name("chat")
                .description("A function to handle chat requests")
                .build();
            
            CompletionSKFunction chatFunction = kernel.getSemanticFunctionBuilder()
                .withPromptTemplate(prompt)
                .withFunctionMetadata(metadata)
                .build();
            
            // Execute the function
            SKContext context = chatFunction.invokeAsync(request.getMessage()).join();
            String response = context.getResult();
            
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
} 