package com.example.semantickerneldemo.model;

public enum ModelType {
    GPT("gpt-35-turbo"),
    MIXTRAL("Mixtral-8x7B-Instruct-v0.1"),
    IMAGEN("imagen");

    private final String deploymentName;

    ModelType(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public String getDeploymentName() {
        return deploymentName;
    }
} 