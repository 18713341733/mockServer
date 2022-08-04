package com.example.mockserver.controller;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@RestController
public class MockController2 {
    // 文件存放路径，常量
    public static final String MOCK_DATA_PATH = "mock_data";
    // 注入
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/**")
    public String doMock() throws IOException {
        // 获取请求的URI
        String requestURI = request.getRequestURI();
        // 将输入的URI，拼成文件的名称
        // /get/order/info => get_order_info
        // 取/get/order/info ，第一个/后面的数据，get/order/info
        requestURI =StringUtils.substringAfter(requestURI,"/");
        // 将/替换成_，将uri拼成文件名称
        String fileName = StringUtils.replace(requestURI, "/", "_");
        // 拼成文件路径
        String filePath = MOCK_DATA_PATH+"/"+fileName;
        // 读取文件
        String response = IOUtils.toString(MockController2.class.getClassLoader().getResourceAsStream(filePath));
        return response;
    }
}
