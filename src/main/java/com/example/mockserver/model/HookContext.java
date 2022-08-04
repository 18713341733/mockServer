package com.example.mockserver.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class HookContext {
    private String finalResponse;
    private Map<String,String> requestParams;
}
