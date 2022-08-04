package com.example.mockserver.model;

import lombok.Data;

import java.util.Map;

@Data
public class MappingParamsEntity {
    private Map<String,String> params;
    private int weight;

}
