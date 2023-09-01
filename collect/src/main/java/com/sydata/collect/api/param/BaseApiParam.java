package com.sydata.collect.api.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.framework.cache.util.ClassFieldMapUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Map;

import static com.sydata.framework.util.StringUtils.isNotEmpty;

/**
 * @author lzq
 * @description API操作参数基类
 * @date 2022/10/21 8:55
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "API操作参数基类")
public abstract class BaseApiParam implements Serializable {

    @NotBlank(message = "操作标志必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[i|d|u]$", message = "操作标志不存在", groups = BasicCheck.class)
    @ApiModelProperty("操作标志")
    private String czbz;

    @NotNull(message = "最后更新时间必填", groups = BasicCheck.class)
    @ApiModelProperty("最后更新时间")
    private LocalDateTime zhgxsj;


    /****************以下字段不需要传输，做为build的缓存**********************/

    @JsonIgnore
    @ApiModelProperty("构建ID")
    private String buildId;

    @JsonIgnore
    @ApiModelProperty("构建企业ID")
    private String buildCompanyId;

    @JsonIgnore
    @ApiModelProperty("构建库存ID")
    private String buildStockHouseId;

    public String getBuildId() {
        try {
            return isNotEmpty(buildId) ? buildId : (buildId = buildId());
        } catch (Exception e) {
            return null;
        }
    }

    public String getBuildCompanyId() {
        try {
            return isNotEmpty(buildCompanyId) ? buildCompanyId : (buildCompanyId = buildCompanyId());
        } catch (Exception e) {
            return null;
        }
    }

    public String getBuildStockHouseId() {
        try {
            return isNotEmpty(buildStockHouseId) ? buildStockHouseId : (buildStockHouseId = buildStockHouseId());
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * 构建库区ID
     *
     * @return 库区ID
     */
    protected String buildStockHouseId() {
        return null;
    }

    /**
     * 构建企业ID
     *
     * @return 企业ID
     */
    protected String buildCompanyId() {
        return null;
    }

    /**
     * 构建ID
     *
     * @return 主键ID
     */
    protected abstract String buildId();


    /**
     * 统计字段总数
     *
     * @return 字段总数
     */
    public int fieldTotalCount() {
        Map<String, Field> fieldMap = ClassFieldMapUtil.mapByClass(this.getClass());
        return (int) StreamEx.of(fieldMap.values())
                .filter(v -> !v.isAnnotationPresent(JsonIgnore.class))
                .count();
    }

    /**
     * 统计字段上传数(不为空的字段数)
     *
     * @return 字段上传数(不为空的字段数)
     */
    public int fieldValidCount() {
        Map<String, Field> fieldMap = ClassFieldMapUtil.mapByClass(this.getClass());
        return (int) StreamEx.of(fieldMap.values())
                .filter(v -> !v.isAnnotationPresent(JsonIgnore.class))
                .map(v -> ClassFieldMapUtil.getFieldVal(this, v))
                .filter(ObjectUtils::isNotEmpty)
                .count();
    }
}
