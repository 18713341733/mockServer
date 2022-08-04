package com.example.mockserver.service;

import com.example.mockserver.chain.ChainManager;
import com.example.mockserver.model.MockContext;
import org.springframework.stereotype.Service;

@Service
public class MockServiceImpl implements MockService{
    @Override
    public String doMock(MockContext mockContext) {

        return ChainManager.of().doMapping(mockContext);
    }
}
