package com.example.semantickerneldemo.service.impl;

import com.example.semantickerneldemo.exception.ModelServiceException;
import com.example.semantickerneldemo.service.AIModelService;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.SKBuilders;
import com.microsoft.semantickernel.chatcompletion.ChatCompletion;
import com.microsoft.semantickernel.promptexecution.PromptExecutionSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AIModelServiceImpl implements AIModelService {
    private final Kernel kernel;
    private final PromptExecutionSettings defaultSettings;

    public AIModelServiceImpl(Kernel kernel) {
        this.kernel = kernel;
        this.defaultSettings = createDefaultSettings();
    }

    @Override
    public String generateResponse(String prompt, String deploymentName, PromptExecutionSettings settings) {
        try {
            log.info("Generating response for deployment: {}", deploymentName);
            ChatCompletion chatCompletion = SKBuilders.chatCompletion()
                    .withKernel(kernel)
                    .withPrompt(prompt)
                    .withExecutionSettings(settings)
                    .withDeploymentName(deploymentName)
                    .build();

            return chatCompletion.invokeAsync().join();
        } catch (Exception e) {
            log.error("Error generating response for deployment: {}", deploymentName, e);
            throw new ModelServiceException("Failed to generate response", e);
        }
    }

    @Override
    public String generateResponse(String prompt, String deploymentName) {
        return generateResponse(prompt, deploymentName, defaultSettings);
    }

    private PromptExecutionSettings createDefaultSettings() {
        return new PromptExecutionSettings.Builder()
                .temperature(0.7)
                .topP(0.95)
                .maxTokens(800)
                .build();
    }
} 