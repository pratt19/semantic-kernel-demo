package com.example.semantickerneldemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "client-openai")
public class OpenAIConfig {
    private String key;
    private String endpoint;
    private String deploymentName;
    private String keyId;
} 