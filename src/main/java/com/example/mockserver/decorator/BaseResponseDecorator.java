package com.example.mockserver.decorator;

public abstract class BaseResponseDecorator<T> implements IDecorator<T>{
    private BaseResponseDecorator<T> decorator;

    // 构造器
    public BaseResponseDecorator(BaseResponseDecorator<T> decorator) {
        this.decorator = decorator;
    }

    // 自己装饰的方法，重写这个方法
    public abstract T onDecorator(T t);

    // 整体调用的逻辑
    public T decorate(T t){
        // 先判断，当前属性是否为空
        if(decorator != null){
            // 不为空，先让下一节decorator装饰
            t = decorator.decorate(t);
            // 再自己装饰一次，一共装饰了2次
            return onDecorator(t);
        }
        // 为空，就调用自己的装饰方法。只装饰一次
        return onDecorator(t);
    }

}
