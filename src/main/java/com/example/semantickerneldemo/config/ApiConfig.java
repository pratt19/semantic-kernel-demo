package com.example.semantickerneldemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ApiConfig {

    @Value("${client-openai-endpoint}")
    private String openAiEndpoint;

    public String getOpenAiEndpoint() {
        return openAiEndpoint;
    }

    public void setOpenAiEndpoint(String openAiEndpoint) {
        this.openAiEndpoint = openAiEndpoint;
    }
} 