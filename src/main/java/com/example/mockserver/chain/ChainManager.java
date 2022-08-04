package com.example.mockserver.chain;

import com.example.mockserver.model.MockContext;

public class ChainManager {
    // 属性就是链条的头
    private AbstractHandler<MockContext,String> handler;
    // 构造器，私有，不能被new
    private ChainManager(){
        // 构造器，给属性赋值。链条的头
        this.handler = initHandler();
    }

    private AbstractHandler<MockContext, String> initHandler() {
        // 串成链条，返回头
        FileHandler fileHandler = new FileHandler();
        DirectoryHandle directoryHandle = new DirectoryHandle();
        fileHandler.setNextHandler(directoryHandle);
        return fileHandler;

    }

    // ClassHolder属于静态内部类，在加载类Demo03的时候，只会加载内部类ClassHolder，
    // 但是不会把内部类的属性加载出来
    private static class ClassHolder{
        // 这里执行类加载，是jvm来执行类加载，它一定是单例的，不存在线程安全问题
        // 这里不是调用，是类加载，是成员变量
        private static final ChainManager holder =new ChainManager();

    }

    public static ChainManager of(){//第一次调用getInstance()的时候赋值
        return ClassHolder.holder;
    }

    // 处理数据
    public String doMapping(MockContext mockContext){
        return handler.doHandle(mockContext);
    }



}
