package com.example.mockserver.decorator;

import com.example.mockserver.model.HookContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonHookDecorator extends BaseResponseDecorator<HookContext>{
    private static final Pattern PATTERN = Pattern.compile("\\$\\{hook:(.*?)\\}");

    public CommonHookDecorator(BaseResponseDecorator<HookContext> decorator) {
        super(decorator);
    }

    @Override
    public HookContext onDecorator(HookContext hookContext) {
        // 拿到返回结果
        String finalResponse = hookContext.getFinalResponse();
        // 拿到请求参数
        Map<String, String> requestParams = hookContext.getRequestParams();

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
        hookContext.setFinalResponse(finalResponse);
        return hookContext;

    }


}
