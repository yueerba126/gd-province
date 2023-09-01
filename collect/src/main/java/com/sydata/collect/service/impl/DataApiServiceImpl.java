package com.sydata.collect.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.register.DataRegister;
import com.sydata.collect.api.service.IData;
import com.sydata.collect.service.IDataApiService;
import com.sydata.collect.vo.DataApiColumnsVo;
import com.sydata.framework.cache.util.ClassFieldMapUtil;
import io.swagger.annotations.ApiModelProperty;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author lzq
 * @description 数据收集-数据API接口实现类
 * @date 2023/4/27 16:27
 */
@Service("dataApiService")
public class DataApiServiceImpl implements IDataApiService {

    @Resource
    private DataRegister dataRegister;

    @Override
    public List<DataApiColumnsVo> columns(String apiCode) {
        DataApiEnum dataApiEnum = DataApiEnum.getByApiCode(apiCode);
        Assert.notNull(dataApiEnum, "数据API不存在");

        IData dataApi = dataRegister.getByDataApi(dataApiEnum);
        Assert.notNull(dataApi, "数据API未实现");

        // 获取接口参数class，并解析接口字段内容
        Class paramClass = dataApi.resolveTypeArgumentsReport()[0];
        Map<String, Field> fieldMap = ClassFieldMapUtil.mapByClass(paramClass);

        return StreamEx.of(fieldMap.values())
                .filter(v -> !v.isAnnotationPresent(JsonIgnore.class))
                .filter(v -> v.isAnnotationPresent(ApiModelProperty.class))
                .map(v -> new DataApiColumnsVo(v.getName(), v.getAnnotation(ApiModelProperty.class).value()))
                .toList();
    }
}
