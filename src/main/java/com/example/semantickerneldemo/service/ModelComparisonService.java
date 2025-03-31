package com.example.semantickerneldemo.service;

import com.example.semantickerneldemo.dto.ModelResponse;
import com.example.semantickerneldemo.model.ModelType;
import com.microsoft.semantickernel.promptexecution.PromptExecutionSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModelComparisonService {
    private final AIModelService aiModelService;

    public List<ModelResponse> compareModels(String prompt, List<ModelType> models) {
        return models.stream()
                .map(model -> CompletableFuture.supplyAsync(() ->
                    new ModelResponse(
                        model.name(),
                        aiModelService.generateResponse(prompt, model.getDeploymentName()),
                        new ModelResponse.ModelConfig(
                            model.getDeploymentName(),
                            0.7,
                            0.95,
                            800
                        )
                    )
                ))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
} 