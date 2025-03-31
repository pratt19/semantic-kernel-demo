package com.example.semantickerneldemo.controller;

import com.example.semantickerneldemo.dto.ModelResponse;
import com.example.semantickerneldemo.model.ModelType;
import com.example.semantickerneldemo.service.ModelComparisonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/models")
@RequiredArgsConstructor
public class ModelController {
    private final ModelComparisonService modelComparisonService;

    @PostMapping("/compare")
    public ResponseEntity<List<ModelResponse>> compareModels(
            @RequestBody @Valid @NotBlank String prompt) {
        List<ModelResponse> responses = modelComparisonService.compareModels(
            prompt,
            List.of(ModelType.GPT, ModelType.MIXTRAL)
        );
        return ResponseEntity.ok(responses);
    }
} 