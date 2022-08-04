package com.example.mockserver.chain;

import com.example.mockserver.model.MockContext;
import lombok.Setter;

import java.io.IOException;

@Setter
public abstract class AbstractHandler<T,R> {
    // 属性是下一节链条
    private AbstractHandler<T,R>  nextHandler;

    // 当前链条是否能处理
    protected abstract boolean preHandle(T t);

    // 具体处理的逻辑
    protected abstract R onHandle(T t) throws Exception;

    // 总的模版处理逻辑
    public R doHandle(T t){
        // 能处理，直接处理
        if (preHandle(t)){
            try {
                return onHandle(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 下一节链条处理
        if (nextHandler != null){
            return nextHandler.doHandle(t);
        }
        // 所有链条都不能处理，抛出异常
        throw new RuntimeException("no chain");
    }
}
