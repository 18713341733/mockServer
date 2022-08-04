package com.example.mockserver.observer;

import com.example.mockserver.model.MockContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 建设点：请求数据 hook
 * 场景：比如请求的时候，请求参数携带一个requestId, 然后requestId本身还是个变化的，也是随机的。
 *      然后在返回的时候，要把这个id带回去，即：虽然返回数据不能写死，但是你也不能自己生成，需要使用请求的参数
 * {"key1":"${random:id:6}","key2":"${random:str:10}","count":3,"person":[{"id":${hook:userId},"name":"张三"},{"id":2,"name":"李四"}],"object":{"id":1,"msg":"对象里的对象"}}
 * ${hook:userId}   =>   userId
 * 实现思路：
 *      1. 要保证request params 有我们需要的参数
 *      2. 设定标记 ${hook:userId}
 *      3. 通过正则 获取参数名
 *      4. 从request params  获取参数值
 *      5. 完成替换
 *      6. 回写
 */
public class HookResponseObserver0 implements IObserver<MockContext> {
    private static final Pattern PATTERN = Pattern.compile("\\$\\{hook:(.*?)\\}");
    @Override
    public void update(MockContext mockContext) {
        // 拿到返回结果
        String finalResponse = mockContext.getFinalResponse();
        // 拿到请求参数
        Map<String, String> requestParams = mockContext.getRequestParams();

        Matcher matcher = PATTERN.matcher(finalResponse);
        while ((matcher.find())){
            String replaceStr = matcher.group(0);
            String paramName = matcher.group(1);
            // 如果用户传参数里不包含要替换的值，直接返回
            if(!requestParams.containsKey(paramName)){
                break;
            }
            String value = requestParams.get(paramName);
            finalResponse = StringUtils.replace(finalResponse,replaceStr,value);
        }
        // 回写数据
        mockContext.setFinalResponse(finalResponse);

    }
}
