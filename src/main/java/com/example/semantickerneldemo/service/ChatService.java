package com.example.semantickerneldemo.service;

import com.example.semantickerneldemo.exception.ChatServiceException;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.semanticfunctions.KernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionDefinition;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class ChatService {

    private final Kernel kernel;

    public ChatService(Kernel kernel) {
        this.kernel = kernel;
    }

    @KernelFunctionDefinition(
        name = "chat",
        description = "Generate a response based on the user's input"
    )
    public String chat(
        @KernelFunctionParameter(name = "input", description = "The user's input message")
        String input
    ) {
        try {
            log.debug("Processing chat request with input: {}", input);
            KernelFunction chatFunction = kernel.getFunction("chat");
            String response = chatFunction.invoke(input).getResult();
            log.debug("Successfully generated response for input: {}", input);
            return response;
        } catch (Exception e) {
            log.error("Error processing chat request: {}", e.getMessage(), e);
            throw new ChatServiceException("Failed to process chat request", e);
        }
    }
} 