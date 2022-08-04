package com.example.mockserver.service;

import com.example.mockserver.model.MockContext;

public interface MockService {
    String doMock(MockContext mockContext);
}
