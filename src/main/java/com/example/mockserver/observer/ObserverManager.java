package com.example.mockserver.observer;

import com.example.mockserver.model.MockContext;
import com.google.common.collect.Lists;

import java.util.List;

public class ObserverManager {
    // 属性就是List。观察者就是遍历List处理同一个数据
    private List<IObserver<MockContext>> observers;
    // 构造器，私有，不能被new
    private ObserverManager(){
        // 构造器，构造这个属性List
        // 这是一个工具实体类的表列
        observers = Lists.newArrayList(
                new LoadMockFileObserver(),// 1、加载本地mock文件，转成我们需要的实体类
                new CalcWeightObserver(), // 2 基于请求的参数，计算权重
                new RealObserver(),     // 插入一个透传
                new PackObserver(),  // 3 处理数据
                new HookResponseObserver(), // 4 hook
                new TimeOutObserver()  // 超时
        );
    }

    // ClassHolder属于静态内部类，在加载类Demo03的时候，只会加载内部类ClassHolder，
    // 但是不会把内部类的属性加载出来
    private static class ClassHolder{
        // 这里执行类加载，是jvm来执行类加载，它一定是单例的，不存在线程安全问题
        // 这里不是调用，是类加载，是成员变量
        private static final ObserverManager holder =new ObserverManager();

    }

    public static ObserverManager of(){//第一次调用getInstance()的时候赋值
        return ClassHolder.holder;
    }

    // 处理数据的方法
    public String getMockData(MockContext mockContext){
        for (IObserver observer:this.observers){
            // 每一个observer，处理mockContext ，都是没有返回值的
            // 我们把所有的结果处理结果，都回写进了mockContext
            // 这里用的for循环，我们处理的是同一个mockContext，修改的变量得以保存
            observer.update(mockContext);
        }
        return mockContext.getFinalResponse();
    }



}
