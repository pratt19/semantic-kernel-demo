package com.example.semantickerneldemo.service;

import com.microsoft.semantickernel.promptexecution.PromptExecutionSettings;

public interface AIModelService {
    String generateResponse(String prompt, String deploymentName, PromptExecutionSettings settings);
    String generateResponse(String prompt, String deploymentName);
} 