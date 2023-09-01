package com.sydata.record.domain;

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
import java.math.BigDecimal;

/**
 * @author lzq
 * @description
 * @date 2022/12/10 12:20
 */
@ApiModel(description = "备案管理-熏蒸方式")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("record_fumigation_way")
public class FumigationWay implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "熏蒸备案ID")
    private String fumigationId;

    @ApiModelProperty(value = "设定熏蒸浓度(ml/m³)")
    private BigDecimal xznd;

    @ApiModelProperty(value = "密闭时间(天)")
    private Integer mbsj;

    @ApiModelProperty(value = "蒸熏方式")
    private String xzfs;

    @ApiModelProperty(value = "散气方式")
    private String sqfs;
}
