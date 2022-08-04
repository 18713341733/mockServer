package com.example.mockserver.decorator;

import com.example.mockserver.model.HookContext;

public class DecoratorManager {
    // 属性
    private IDecorator<String> packDecorator;
    private IDecorator<HookContext> hookDecorator;


    // 构造器，私有，不能被new
    private DecoratorManager(){
        packDecorator = new RandomIdDecorator(new RandomStrDecorator(null));
        // 这个hook链条，目前只有一个
        hookDecorator = new CommonHookDecorator(null);

    }

    // ClassHolder属于静态内部类，在加载类Demo03的时候，只会加载内部类ClassHolder，
    // 但是不会把内部类的属性加载出来
    private static class ClassHolder{
        // 这里执行类加载，是jvm来执行类加载，它一定是单例的，不存在线程安全问题
        // 这里不是调用，是类加载，是成员变量
        private static final DecoratorManager holder =new DecoratorManager();

    }

    public static DecoratorManager of(){//第一次调用getInstance()的时候赋值
        return ClassHolder.holder;
    }

    public String doPack(String response){
        return packDecorator.decorate(response);
    }

    public HookContext doHook(HookContext hookContext){
        return this.hookDecorator.decorate(hookContext);
    }


}
