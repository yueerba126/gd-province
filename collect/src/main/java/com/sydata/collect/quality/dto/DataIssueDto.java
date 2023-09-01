package com.sydata.collect.quality.dto;

import cn.hutool.extra.validation.BeanValidationResult;
import com.sydata.framework.cache.util.FieldNameFunction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author lzq
 * @description 数据质量-数据问题DTO
 * @date 2023/4/23 15:51
 */
@ApiModel(description = "数据质量-数据问题DTO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DataIssueDto implements Serializable {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "数据ID")
    private String dataId;

    @ApiModelProperty(value = "数据问题明细DTO列表")
    private List<DataIssueDtlDto> dtls;


    /**
     * 追加问题明细
     *
     * @param condition   条件
     * @param fieldName   字段名
     * @param issueRemark 问题说明
     */
    public <T> void append(boolean condition, FieldNameFunction<T, Object> fieldName, String issueRemark) {
        if (condition) {
            return;
        }
        append(fieldName.getFieldName(), issueRemark);
    }

    /**
     * 追加问题明细
     *
     * @param condition   条件
     * @param fieldName   字段名
     * @param issueRemark 问题说明
     */
    public <T> void append(boolean condition, FieldNameFunction<T, Object> fieldName, Supplier<String> issueRemark) {
        if (condition) {
            return;
        }
        append(fieldName.getFieldName(), issueRemark.get());
    }

    /**
     * 追加问题明细
     *
     * @param fieldName   字段名
     * @param issueRemark 问题说明
     */
    public <T> void append(FieldNameFunction<T, Object> fieldName, String issueRemark) {
        append(fieldName.getFieldName(), issueRemark);
    }

    /**
     * 追加问题明细
     *
     * @param condition   条件
     * @param fieldName   字段名
     * @param issueRemark 问题说明
     */
    public void append(boolean condition, String fieldName, String issueRemark) {
        if (condition) {
            return;
        }

        append(fieldName, issueRemark);
    }

    /**
     * 追加问题明细
     *
     * @param result 字段名
     */
    public void append(BeanValidationResult result) {
        if (result.isSuccess()) {
            return;
        }
        result.getErrorMessages().forEach(message -> append(message.getPropertyName(), message.getMessage()));
    }

    /**
     * 追加问题明细
     *
     * @param fieldName   字段名
     * @param issueRemark 问题说明
     */
    public void append(String fieldName, String issueRemark) {
        dtls.add(buildDataIssueDtl(fieldName, issueRemark));
    }


    /**
     * 追加明细列表
     *
     * @param dtls 数据问题明细DTO列表
     */
    public void append(List<DataIssueDtlDto> dtls) {
        dtls.forEach(v -> v.setIssueDataId(this.id));
        dtls.addAll(dtls);
    }

    /**
     * 构建数据问题明细
     *
     * @param fieldName   字段名
     * @param issueRemark 问题说明
     * @return 数据问题明细
     */
    private DataIssueDtlDto buildDataIssueDtl(String fieldName, String issueRemark) {
        return new DataIssueDtlDto()
                .setIssueDataId(this.id)
                .setFieldName(fieldName)
                .setIssueRemark(issueRemark);
    }

}
