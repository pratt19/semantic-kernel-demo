package com.example.semantickerneldemo.dto;

import com.example.semantickerneldemo.config.ModelConfig;

public record ModelResponse(
    String modelName,
    String response,
    ModelConfig config
) {} 