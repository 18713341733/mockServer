package com.example.mockserver.controller;

import cn.hutool.core.io.FileUtil;
import com.example.mockserver.model.MappingParamsEntity;
import com.example.mockserver.model.MockContext;
import com.example.mockserver.model.MockDataInfo;
import com.example.mockserver.service.MockService;
import com.example.mockserver.util.ArrayUtil;
import com.example.mockserver.util.YamlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class MockController {



    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MockService mockService;

    @RequestMapping("/**")
    public String doMock() throws IOException {
        log.info("---------:"+request.getRequestURI());

        // 将获取的用户数据 ip 参数 URI ，存储到 mockContext 这个类里
        MockContext mockContext = MockContext.builder()
                .requestIp(request.getRemoteAddr()) // 获取ip
                .requestParams(getParams(request.getParameterMap()))
                .requestURI(request.getRequestURI()) // 获取请求的URI
                .build();

        String response = mockService.doMock(mockContext);

        return response ;

    }


    // 获取用户的传参，value是一个数组。这里为了将来处理方便，我们将这数组转成一个字符串。
    // 我们默认，这个数据的长度是1，那我们只需要取出来数组的第一个值就可以了。
    public Map<String,String> getParams(Map<String,String[]> parameterMap){
        Map<String,String> params = parameterMap.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(),e -> ArrayUtil.getFirst(e.getValue())));
        return params;

    }


}
