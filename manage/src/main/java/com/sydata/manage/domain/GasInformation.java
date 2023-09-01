package com.sydata.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 气体信息表
 * </p>
 *
 * @author lzq
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("manage_gas_information")
@ApiModel(value = "GasInformation对象", description = "气体信息表")
public class GasInformation extends BaseFiledEntity implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键id-气体浓度检测单号")
    private String id;

    @ApiModelProperty(value = "气体浓度检测单号:由货位代码+检测日期（yyyyMMdd）+4位顺序号组成")
    private String qtndjcdh;

    @ApiModelProperty(value = "检测时间")
    private LocalDateTime jcsj;

    @ApiModelProperty(value = "货位编码")
    private String hwdm;

    @ApiModelProperty(value = "氧气含量值集合")
    private String yqhlzjh;

    @ApiModelProperty(value = "二氧化碳含量集合")
    private String eyhthlzjh;

    @ApiModelProperty(value = "硫酰氟浓度集合")
    private String lxfndzjh;

    @ApiModelProperty(value = "磷化氢浓度集合")
    private String lhqndzjh;

    @ApiModelProperty(value = "一氧化氮含量集合")
    private String yyhdhlzjh;

    @ApiModelProperty(value = "一氧化碳含量集合")
    private String yyhthlzjh;

    @ApiModelProperty(value = "作业类型")
    private String zylx;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}
