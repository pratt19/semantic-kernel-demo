package com.example.semantickerneldemo.config;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.SemanticKernel;
import com.microsoft.semantickernel.connectors.ai.openai.OpenAIChatCompletion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SemanticKernelConfig {

    @Value("${client-openai-key}")
    private String openAiKey;

    @Value("${client-openai-endpoint}")
    private String openAiEndpoint;

    @Value("${client-openai-deployment-name}")
    private String openAiDeploymentName;

    @Bean
    public Kernel kernel() {
        OpenAIChatCompletion chatCompletion = new OpenAIChatCompletion(
            openAiDeploymentName,
            openAiKey,
            openAiEndpoint
        );

        return SemanticKernel.builder()
                .withAIService(chatCompletion)
                .build();
    }
} 