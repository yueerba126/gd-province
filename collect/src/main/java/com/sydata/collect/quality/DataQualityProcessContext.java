package com.sydata.collect.quality;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.service.IData;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.experimental.Accessors;
import one.util.streamex.StreamEx;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.function.Function.identity;

/**
 * @author lzq
 * @description 数据质量处理上下文
 * @date 2023/4/23 15:05
 */
@Data
@ToString
@Accessors(chain = true)
public class DataQualityProcessContext {

    @ApiModelProperty(value = "数据API枚举")
    private DataApiEnum dataApiEnum;

    @ApiModelProperty(value = "数据模型服务类")
    private IData<BaseApiParam, BaseFiledEntity, Object> dataApi;

    @ApiModelProperty(value = "数据ID列表")
    private Set<String> dataIds;

    @ApiModelProperty(value = "实体映射")
    private Map<String, BaseFiledEntity> entityMap;

    @ApiModelProperty(value = "参数映射")
    private Map<String, BaseApiParam> paramMap;

    @ApiModelProperty(value = "数据问题容器")
    private Map<String, DataIssueDto> dataIdIssueDtoMap;

    @ApiModelProperty(value = "线程数")
    private int threads;

    @ApiModelProperty(value = "数据问题插入成功ID集合")
    private Set<String> issueSaveSuccessIds;

    private DataQualityProcessContext() {
    }

    /**
     * 申请数据质量处理上下文
     *
     * @param dataApiEnum 数据API枚举
     * @param entityList  数据实体列表
     * @param dataApi     数据模型服务类
     * @param threads     线程池数
     * @return 上报数据处理上下文
     */
    public static DataQualityProcessContext apply(DataApiEnum dataApiEnum,
                                                  List<BaseFiledEntity> entityList,
                                                  IData<BaseApiParam, BaseFiledEntity, Object> dataApi,
                                                  int threads) {

        // 实体映射
        Map<String, BaseFiledEntity> entityMap = StreamEx.of(entityList).toMap(BaseFiledEntity::getId, identity());

        // API参数映射
        Map<String, BaseApiParam> paramMap = StreamEx.of(entityList).toMap(BaseFiledEntity::getId, dataApi::toParam);

        return new DataQualityProcessContext()
                .setDataIdIssueDtoMap(MapUtil.newConcurrentHashMap(entityList.size()))
                .setDataApiEnum(dataApiEnum)
                .setDataApi(dataApi)
                .setDataIds(entityMap.keySet())
                .setEntityMap(entityMap)
                .setParamMap(paramMap)
                .setThreads(threads);
    }

    /**
     * 记录问题
     *
     * @param dataId 数据ID
     * @return 数据问题
     */
    public DataIssueDto issueRecord(String dataId) {
        return dataIdIssueDtoMap.computeIfAbsent(dataId, this::buildIssueDto);
    }

    /**
     * 构建数据问题实体对象
     *
     * @param dataId 数据ID
     * @return 数据问题
     */
    @SneakyThrows(Throwable.class)
    private DataIssueDto buildIssueDto(String dataId) {
        return new DataIssueDto().setId(IdWorker.getIdStr()).setDataId(dataId).setDtls(new CopyOnWriteArrayList<>());
    }
}
