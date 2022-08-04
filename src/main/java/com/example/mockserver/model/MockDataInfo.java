package com.example.mockserver.model;

import lombok.Data;

import java.util.List;
@Data
public class MockDataInfo {
    private String mappingHost;
    private String response;
    private List<MappingParamsEntity> mappingParams;
    private Long timeout;
    private String realUrl;


}

