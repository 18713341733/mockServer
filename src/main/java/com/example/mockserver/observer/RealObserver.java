package com.example.mockserver.observer;

import com.example.mockserver.model.MockContext;

import java.util.Map;

/**
 * 建设点：透传请求
 * 场景：
 *  比如10个请求，请求mock服务，其中参数id=123的走mock，id=456的走真实的服务。
 *  所以这个时候，如果我们判断id=456了，我们需要去自己真实的拿着请求的参数，
 *  我们再去调真实服务，拿到返回结果，在返回给调用端
 * 实现思路：过来一个请求，做筛选，请求真实地址
 *  1、yml中添加一个属性，用来保存真实地址
 *  2、实体类添加相应的属性
 *  3、将真实地址 set mockContext
 *  4、判断是否走真实地址，如果走则请求真实地址
 *
 */

public class RealObserver implements IObserver<MockContext>{
    @Override
    public void update(MockContext mockContext) {
        if(!mockContext.isRealUrlSet()){
            return; // 没有设置真实url，则直接返回
        }

        // url
        String realUrl = mockContext.getRealUrl();
        // 请求参数
        Map<String, String> requestParams = mockContext.getRequestParams();

    }

}
