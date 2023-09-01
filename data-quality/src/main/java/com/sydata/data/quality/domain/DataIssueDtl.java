package com.sydata.data.quality.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 数据质量-数据问题明细对象 data_quality_data_issue_dtl
 *
 * @author system
 * @date 2023-04-18
 */
@ApiModel(description = "数据质量-数据问题明细")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("data_quality_data_issue_dtl")
public class DataIssueDtl implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "数据问题ID")
    private String issueDataId;

    @ApiModelProperty(value = "字段名")
    private String fieldName;

    @ApiModelProperty(value = "问题说明")
    private String issueRemark;
}