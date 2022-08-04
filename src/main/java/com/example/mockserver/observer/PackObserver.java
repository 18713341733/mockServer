package com.example.mockserver.observer;

import com.example.mockserver.decorator.DecoratorManager;
import com.example.mockserver.model.MockContext;
import com.example.mockserver.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;

public class PackObserver implements IObserver<MockContext> {
//    @Override
//    public void update(MockContext mockContext) {
//        String finalResponse = mockContext.getFinalResponse();
//        // random -> 随机字符
//        String packResponse  =  StringUtils.replace(finalResponse,"${random}", RandomUtil.random());
//        mockContext.setFinalResponse(packResponse);
//
//    }

    @Override
    public void update(MockContext mockContext) {
        String finalResponse = mockContext.getFinalResponse();
        // random -> 随机字符
        String packResponse  = DecoratorManager.of().doPack(finalResponse);
        mockContext.setFinalResponse(packResponse);

    }


}
