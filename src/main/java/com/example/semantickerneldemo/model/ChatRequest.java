package com.example.semantickerneldemo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChatRequest {
    @NotBlank(message = "Input cannot be empty")
    @Size(min = 1, max = 1000, message = "Input must be between 1 and 1000 characters")
    private String input;
} 