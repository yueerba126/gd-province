package com.sydata.basis.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 财务报信息对象 basis_finance
 *
 * @author system
 * @date 2022-12-07
 */
@ApiModel(description = "财务报表信息")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("basis_finance")
public class Finance extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键ID（单位代码-报表时间-报表名-指标序号）")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "报表时间")
    private String bbsj;

    @ApiModelProperty(value = "报表名 01资产负债表 02现金流量表 03利润表")
    private String bbm;

    @ApiModelProperty(value = "指标序号")
    private Integer zbxh;

    @ApiModelProperty(value = "指标名称")
    private String zbmc;

    @ApiModelProperty(value = "指标值1")
    private String zbz1;

    @ApiModelProperty(value = "指标值2")
    private String zbz2;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime zhgxsj;

    @ApiModelProperty(value = "报表类型：年报、月报、日报")
    private String type;
}