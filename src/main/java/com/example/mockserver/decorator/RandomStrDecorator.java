package com.example.mockserver.decorator;

import com.example.mockserver.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomStrDecorator extends BaseResponseDecorator<String>{

    private static final Pattern PATTERN = Pattern.compile("\\$\\{random:str:(\\d+?)\\}");
    // 构造器
    public RandomStrDecorator(BaseResponseDecorator<String> decorator) {
        super(decorator);
    }

    @Override
    public String onDecorator(String data) {
        Matcher matcher = PATTERN.matcher(data);
        while (matcher.find()){
            String replaceStr = matcher.group(0);
            int size = Integer.parseInt(matcher.group(1));
            // 替换
            data = StringUtils.replace(data,replaceStr, RandomUtil.randomStr(size));
        }
        return data;
    }

}
