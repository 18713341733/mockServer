package com.example.mockserver.chain;

import cn.hutool.core.io.FileUtil;
import com.example.mockserver.model.MockContext;
import com.example.mockserver.observer.ObserverManager;

public class DirectoryHandle extends AbstractHandler<MockContext,String> {
    @Override
    protected boolean preHandle(MockContext mockContext) {
        // 判断是否是目录
        return FileUtil.isDirectory(mockContext.getFilePath());
    }

    @Override
    protected String onHandle(MockContext mockContext) throws Exception {
        return ObserverManager.of().getMockData(mockContext);
    }
}
