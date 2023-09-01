package com.sydata.collect.api.service;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.reflect.GenericTypeUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.quality.DataQualityProcessContext;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.framework.util.BeanUtils;
import lombok.SneakyThrows;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @author lzq
 * @description 数据顶级接口
 * @date 2022/10/19 9:51
 */
public interface IData<P, T, R> extends IService<T> {

    /**
     * 数据API枚举
     *
     * @return 数据API枚举
     */
    DataApiEnum api();

    /**
     * 处理数据收集
     *
     * @param param 请求参数
     * @return 数据ID
     */
    String collect(P param);


    /**
     * 参数自定义校验
     *
     * @param dataIssueDto 数据问题DTO
     * @param param        参数
     */
    default void validated(DataIssueDto dataIssueDto, P param) {
    }

    /**
     * 数据质量自定义校验
     *
     * @param context 数据质量处理上下文
     */
    default void dataQualityCustomCheck(DataQualityProcessContext context) {

    }


    /**
     * 获取IData接口上的泛型class数组
     *
     * @return 泛型class数组
     */
    default Class<?>[] resolveTypeArgumentsReport() {
        return GenericTypeUtils.resolveTypeArguments(this.getClass(), IData.class);
    }

    /**
     * 收集参数json转换为收集参数实例
     *
     * @param paramJson 收集参数json
     * @return 收集参数实例
     */
    @SneakyThrows(Throwable.class)
    default P toParam(String paramJson) {
        ObjectMapper objectMapper = SpringUtil.getBean(MappingJackson2HttpMessageConverter.class).getObjectMapper();
        Class<P> pClass = (Class<P>) resolveTypeArgumentsReport()[0];
        return objectMapper.readValue(paramJson, pClass);
    }

    /**
     * 收集参数转换为实体
     *
     * @param param 收集参数
     * @return 实体
     */
    default T toEntity(P param) {
        Class<T> tClass = (Class<T>) resolveTypeArgumentsReport()[1];
        return BeanUtils.copyByClass(param, tClass);
    }

    /**
     * 收集参数转换为上报参数
     *
     * @param param 收集参数
     * @return 上报参数
     */
    default R toReport(P param) {
        Class<R> rClass = (Class<R>) resolveTypeArgumentsReport()[2];
        return BeanUtils.copyByClass(param, rClass);
    }

    /**
     * 实体转换为收集参数
     *
     * @param entity 收集参数
     * @return 实体
     */
    default P toParam(T entity) {
        Class<P> pClass = (Class<P>) resolveTypeArgumentsReport()[0];
        return BeanUtils.copyByClass(entity, pClass);
    }
}
