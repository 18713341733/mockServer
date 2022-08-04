package com.example.mockserver.model;

import com.example.mockserver.consts.MockConst;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class MockContext {
    // 用户传入信息
    // 一次用户请求，对应一个MockContext
    private String requestURI;
    private Map<String,String> requestParams;
    private String requestIp;

    // 返回结果的List
    // 一个接口，对应的返回结果，是一个List
    private List<MockDataInfo> mockDataInfoList;
    private String finalResponse;
    private Long timeout;
    private boolean timeoutSet;
    private String realUrl;
    private boolean realUrlSet;

    public void setRealUrl(String realUrl) {
        if(StringUtils.isNotEmpty(realUrl)){
            this.realUrl = realUrl;
            this.realUrlSet = true;
        }
    }

    public void setTimeout(Long timeout){
        if(timeout != null && timeout >0 ){
            this.timeout = timeout;
            timeoutSet = true;
        }
    }

    // 根据uri，得到文件名
    // /get/order/info -> get_order_info
    // 去掉第一个/ ，取后面的字符串
    public String getFileName(){
        String str = StringUtils.substringAfter(this.requestURI, "/");
        String fileName = StringUtils.replace(str, "/", "_");
        return fileName;
    }

    // 得到文件的路径
    public String getFilePath(){
        String filePath = MockConst.MOCK_DATA_PATH+"/"+this.getFileName();
        return filePath;
    }
    // 将用户传参，组成一个k=v 的List
    public List<String> getParamStringList(){
        // 计算权重的方法
        // 用户的传参，mockContext.getRequestParams()，是一个Map
        // 将用户的传参Map，转换成list。
        // 如 k:v, id:123,name:zhangsan 转换成 【"id=123","name=zhangsan"]
        List<String> paramStrList = this.getRequestParams().entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.toList());
        return paramStrList;
    }


}
