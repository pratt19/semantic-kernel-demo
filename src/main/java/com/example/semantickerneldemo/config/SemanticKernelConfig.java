package com.example.semantickerneldemo.config;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.connectors.ai.openai.util.OpenAIClientProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SemanticKernelConfig {

    @Value("${dial.api.base-url}")
    private String baseUrl;

    @Value("${dial.api.key}")
    private String apiKey;

    @Bean
    public Kernel kernel() {
        return new Kernel.Builder()
                .withAIService(
                    OpenAIClientProvider.builder()
                        .withBaseUrl(baseUrl)
                        .withApiKey(apiKey)
                        .build(),
                    true
                )
                .build();
    }
} 