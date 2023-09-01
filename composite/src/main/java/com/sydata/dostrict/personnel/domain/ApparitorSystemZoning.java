package com.sydata.dostrict.personnel.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
import java.time.LocalDateTime;

/**
 * 行政管理-人员制度管理管理行政区划对象 apparitor_system_zoning
 *
 * @author fuql
 * @date 2023-04-24
 */
@ApiModel(description = "行政管理-人员制度管理管理行政区划")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("apparitor_system_zoning")
public class ApparitorSystemZoning implements Serializable {


    @Excel(name = "人员制度管理管理行政区划id")
    @ApiModelProperty(value = "人员制度管理管理行政区划id")
    @TableId(value = "zoning_id", type = IdType.AUTO)
    private Long zoningId;

    @Excel(name = "人员制度管理id")
    @ApiModelProperty(value = "人员制度管理id")
    private String systemId;

    @Excel(name = "行政区划Id")
    @ApiModelProperty(value = "行政区划Id")
    private String regionId;

    @Excel(name = "创建者")
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @Excel(name = "创建时间")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @Excel(name = "最后更新者")
    @ApiModelProperty(value = "最后更新者")
    private String updateBy;

    @Excel(name = "最后更新时间")
    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updateTime;
}