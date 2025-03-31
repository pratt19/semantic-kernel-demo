package com.example.semantickerneldemo.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ModelConfig {
    private final String deploymentName;
    private final double temperature;
    private final double topP;
    private final int maxTokens;
} 