package com.sydata.framework.databind.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.framework.databind.utils.NumberUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

/**
 * 数据绑定远端查询对象
 *
 * @author zhoucl
 * @date 2021/6/25 17:16
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataBindQuery<T> implements Serializable {

    @ApiModelProperty("查询数据库唯一值列")
    private Object queryColumn;

    @ApiModelProperty("查询时附带组合条件")
    private Object queryAndSql;

    @ApiModelProperty("查询值")
    private Object queryKey;

    @ApiModelProperty("缓存key")
    @JsonIgnore
    private String cacheKey;

    @ApiModelProperty("查询值组合")
    private Object sourceFieldCombination;

    @ApiModelProperty("组织id")
    private Long tenantId;

    @ApiModelProperty("远端查询返回真实结果")
    private T data;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataBindQuery<?> that = (DataBindQuery<?>) o;
        Object thatQueryKey = that.queryKey;
        if (queryKey instanceof Number && that.queryKey instanceof Number) {
            thatQueryKey = NumberUtils.convertNumberToTargetClass((Number) that.queryKey, queryKey.getClass());
        }
        return Objects.equals(queryColumn, that.queryColumn) &&
                Objects.equals(queryAndSql, that.queryAndSql) &&
                Objects.equals(queryKey, thatQueryKey) &&
                Objects.equals(sourceFieldCombination, that.sourceFieldCombination) &&
                Objects.equals(tenantId, that.tenantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryColumn, queryAndSql, queryKey, sourceFieldCombination, tenantId);
    }
}
