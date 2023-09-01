package com.sydata.collect.api.register;

import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.service.IData;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;

/**
 * @author lzq
 * @description 数据接口注册中心
 * @date 2022/10/19 10:55
 */
@Component
public class DataRegister {

    private Map<DataApiEnum, IData> dataApiMap;

    public DataRegister(List<IData> data) {
        this.dataApiMap = StreamEx.of(data).toMap(IData::api, identity());
    }

    /**
     * 获取数据收集接口实例
     *
     * @param dataApiEnum 数据API枚举
     * @return 数据收集接口实例
     */
    public IData getByDataApi(DataApiEnum dataApiEnum) {
        return dataApiMap.get(dataApiEnum);
    }
}
