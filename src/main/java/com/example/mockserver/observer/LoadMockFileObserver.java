package com.example.mockserver.observer;

import com.example.mockserver.model.MockContext;
import com.example.mockserver.model.MockDataInfo;
import com.example.mockserver.util.YamlUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 加载本地mock文件，转成我们需要的实体类List
 */
public class LoadMockFileObserver implements IObserver<MockContext>{
    @Override
    public void update(MockContext mockContext) {
        // 根据请求的目录，获取目录下所有的文件
        File[] files = new File(mockContext.getFilePath()).listFiles();
        List<MockDataInfo> mockDataInfoList = Arrays.stream(files)
                // 转换，把每一个文件转成对象MockDataInfo
                .map(f -> YamlUtil.readForObject(f.getAbsolutePath(), MockDataInfo.class))
                // 将数组，转成List
                .collect(Collectors.toList());

        // 将一个接口，对应的所有返回信息List ，回写进MockContext
        mockContext.setMockDataInfoList(mockDataInfoList);
    }
}
