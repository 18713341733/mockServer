package com.example.mockserver.observer;

import com.example.mockserver.model.MappingParamsEntity;
import com.example.mockserver.model.MockContext;
import com.example.mockserver.model.MockDataInfo;
import com.example.mockserver.util.YamlUtil;

import java.io.File;
import java.util.List;

/**
 * 计算一个接口对应的所有文件（List对象）的权重,返回权重大的结果
 */
public class CalcWeightObserver implements IObserver<MockContext>{
    @Override
    public void update(MockContext mockContext) {

        // 定义最终的权重结果 和最终的response
        int weightResult = 0;
        String response = "";
        Long timeout = null;
        String realUrl = null;
        for(MockDataInfo mockDataInfo: mockContext.getMockDataInfoList()){

            // 取出实体类的参数，dd得到当前对象的参数list
            List<MappingParamsEntity> mappingParams = mockDataInfo.getMappingParams();
            int weight = 0;
            for (MappingParamsEntity mappingParamsEntity:mappingParams){
                // 将参数转成k=v
                String paramStr = mappingParamsEntity.getParams().entrySet().stream()
                        .map(e -> e.getKey()+"="+e.getValue() )
                        .findFirst().get(); // 我们这里的Map，只有一个值

                // 判断 我们yml文件里指定的参数策略，在不在用户传参url的参数列表里。
                if(mockContext.getParamStringList().contains(paramStr)){
                    // 如果在，则累计权重
                    weight = weight + mappingParamsEntity.getWeight();
                }
            }

            // 每一个文件的权重比较大小，最终返回权重最大的response
            if(weight>weightResult){
                weightResult = weight;
                response = mockDataInfo.getResponse();
                timeout = mockDataInfo.getTimeout();
                realUrl = mockDataInfo.getRealUrl();
            }

        }

        mockContext.setFinalResponse(response);
        mockContext.setTimeout(timeout);
        mockContext.setRealUrl(realUrl);


    }
}
