package com.sydata.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 安全管理
 *
 * @author lzq
 * @date 2022/8/19 11:26
 */
@ApiModel(value = "com-sydata-manage-domain-SafetyCheck")
@Data
@TableName(value = "manage_safety_check")
public class SafetyCheck extends BaseFiledEntity implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "Id主键 库区代码+风险点编码")
    private String id;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "地点")
    private String dd;

    @ApiModelProperty(value = "风险点编码:6位行政区划代码+8位风险识别日期（YYYYMMDD）+4位风险顺序号")
    private String fxdbm;

    @ApiModelProperty(value = "识别人")
    private String sbr;

    @ApiModelProperty(value = "环节/部位")
    private String hjbw;

    @ApiModelProperty(value = "风险信息")
    private String fxxx;

    @ApiModelProperty(value = "风险类型")
    private String fxlx;

    @ApiModelProperty(value = "风险分级")
    private String fxfj;

    @ApiModelProperty(value = "风险管控措施")
    private String fxglcs;

    @ApiModelProperty(value = "隐患信息")
    private String yhxx;

    @ApiModelProperty(value = "隐患排查信息")
    private String yhpcxx;

    @ApiModelProperty(value = "隐患整改信息")
    private String yhzgxx;

    @ApiModelProperty(value = "隐患整改验收信息")
    private String yhysxx;

    @ApiModelProperty(value = "事故基本信息")
    private String sgjbxx;

    @ApiModelProperty(value = "整改时限")
    private LocalDate zgsx;

    @ApiModelProperty(value = "责任单位")
    private String zrdw;

    @ApiModelProperty(value = "责任人")
    private String zrr;

    @ApiModelProperty(value = "整改验收信息")
    private String zgysxx;

    @ApiModelProperty(value = "风险跟踪监管责任人")
    private String fxgzjgzrr;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}