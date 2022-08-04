package com.example.mockserver.controller;

import cn.hutool.core.io.FileUtil;
import com.example.mockserver.model.MappingParamsEntity;
import com.example.mockserver.model.MockContext;
import com.example.mockserver.model.MockDataInfo;
import com.example.mockserver.util.YamlUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@RestController
public class MockController4 {
    public static final String MOCK_DATA_PATH= "/Users/zhaohui/IdeaProjects/mock-server/src/main/resources/mock_data";


    @Autowired
    private HttpServletRequest request;

//    @RequestMapping("/**")
    public String doMock() throws IOException {
        // 获取ip
        String remoteAddr = request.getRemoteAddr();
        // 获取请求的URI
        String uri = request.getRequestURI();
        // 获取参数，注意这里的value是一个数组[]
        Map<String,String[]> parameterMap = request.getParameterMap();
        // 获取用户的传参，value是一个数组。这里为了将来处理方便，我们将这数组转成一个字符串。
        // 我们默认，这个数据的长度是1，那我们只需要取出来数组的第一个值就可以了。
        Map<String,String> collect = getParams(parameterMap);



        // 将获取的用户数据 ip 参数 URI ，存储到 mockContext 这个类里
        MockContext mockContext = MockContext.builder()
                .requestIp(remoteAddr)
                .requestParams(collect)
                .requestURI(uri)
                .build();


        // 计算权重的方法
        // 用户的传参，mockContext.getRequestParams()，是一个Map
        // 将用户的传参Map，转换成list。
        // 如 k:v, id:123,name:zhangsan 转换成 【"id=123","name=zhangsan"]
        List<String> paramStrList = mockContext.getRequestParams().entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.toList());

        // 得到文件的路径
        String filePath = MOCK_DATA_PATH+"/"+mockContext.getFileName();


        File file = new File(filePath);


        // 判断filePath是否是文件
        if (FileUtil.isFile(filePath)) {
            // 是文件，则直接读取文件内容
            return FileUtils.readFileToString(file,"utf-8");
        }

        // 如果是文件夹,获取所有文件。是一个数组
        // 取出所有的文件
        File[] files = file.listFiles();
        // 定义最终的权重结果 和最终的response
        int weightResult = 0;
        String response = "";
        // 遍历所有的文件
        for(File f:files){
            // 循环，将每个文件都转成一个对象。把yml文件转成实体类
            MockDataInfo mockDataInfo = YamlUtil.readForObject(f.getAbsolutePath(), MockDataInfo.class);
            // 取出实体类的参数，得到当前对象的参数list
            List<MappingParamsEntity> mappingParams = mockDataInfo.getMappingParams();
            int weight = 0;
            //
            for (MappingParamsEntity mappingParamsEntity:mappingParams){
                // 将参数转成k=v
                String paramStr = mappingParamsEntity.getParams().entrySet().stream()
                        .map(e -> e.getKey()+"="+e.getValue() )
                        .findFirst().get(); // 我们这里的Map，只有一个值

                // 判断 我们yml文件里指定的参数策略，在不在用户传参url的参数列表里。
                if(paramStrList.contains(paramStr)){
                    // 如果在，则累计权重
                    weight = weight + mappingParamsEntity.getWeight();
                }
            }

            // 每一个文件的权重比较大小，最终返回权重最大的response
            if(weight>weightResult){
                weightResult = weight;
                response = mockDataInfo.getResponse();
            }

        }
        return response;

    }


    // 获取用户的传参，value是一个数组。这里为了将来处理方便，我们将这数组转成一个字符串。
    // 我们默认，这个数据的长度是1，那我们只需要取出来数组的第一个值就可以了。
    public Map<String,String> getParams(Map<String,String[]> parameterMap){
        Map<String,String> params = parameterMap.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(),e -> getFirst(e.getValue())));
        return params;

    }

    // 数组取第一个值，封装了一个方法，做了异常处理。
    public String getFirst(String[] arr){
        if(arr.length==0 || arr == null){
            return "";
        }
        return arr[0];

    }
}
