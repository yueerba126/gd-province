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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 基础信息-设备信息对象 basis_device
 *
 * @author lzq
 * @date 2022-07-08
 */
@ApiModel(description = "基础信息-设备信息")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("basis_device")
public class Device extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键id(库区代码-设备编号)")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty("库区名称")
    private String kqmc;

    @ApiModelProperty("设备编号")
    private String sbbh;

    @ApiModelProperty("设备仪器名称")
    private String sbyqmc;

    @ApiModelProperty("设备仪器代码")
    private String sbyqdm;

    @ApiModelProperty("设备规格型号")
    private String sbggxh;

    @ApiModelProperty("生产厂家")
    private String sccj;

    @ApiModelProperty("生产日期")
    private LocalDate scrq;

    @ApiModelProperty("设备描述")
    private String sbms;

    @ApiModelProperty("设备状态")
    private String sbzt;

    @ApiModelProperty("检定时间")
    private LocalDate jdsj;

    @ApiModelProperty("检定单位")
    private String jddw;

    @ApiModelProperty("操作标志")
    private String czbz;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime zhgxsj;
}