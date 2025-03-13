package com.example.semantickerneldemo.mapper;

import com.example.semantickerneldemo.model.ChatRequest;
import com.example.semantickerneldemo.model.ChatResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    
    @Mapping(target = "input", source = "input")
    @Mapping(target = "response", source = "response")
    ChatResponse toResponse(ChatRequest request, String response);
} 