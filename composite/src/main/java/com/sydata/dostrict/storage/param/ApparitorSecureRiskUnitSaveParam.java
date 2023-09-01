package com.sydata.dostrict.storage.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @date 2022-04-26
 * @description 安全仓储-安全风险台账-新增参数
 * @version: 1.0
 */
@ApiModel(description = "安全仓储-安全风险台账-新增参数")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorSecureRiskUnitSaveParam implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "安全仓储-安全风险台账id")
    private String id;

    @ApiModelProperty(name = "beginDateTime" , value = "整改开始时间")
    private LocalDateTime beginDateTime;

    @ApiModelProperty(name = "endDateTime" , value = "整改结束时间")
    private LocalDateTime endDateTime;

    @ApiModelProperty(name = "beginImg" , value = "整改前照片")
    private String beginImg;

    @ApiModelProperty(name = "corporation" , value = "企业法人")
    private String corporation;

    @ApiModelProperty(name = "dwdm" , value = "企业单位")
    private String dwdm;

    @ApiModelProperty(name = "endImg" , value = "整改后照片")
    private String endImg;

    @ApiModelProperty(name = "headName" , value = "整改负责人")
    private String headName;

    @ApiModelProperty(name = "inspectionId" , value = "检查人ID")
    private String inspectionId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "inspectionTime" , value = "检查时间")
    private LocalDateTime inspectionTime;

    @ApiModelProperty(name = "kqdm" , value = "库区代码")
    private String kqdm;

    @ApiModelProperty(name = "remark" , value = "隐患及问题描述")
    private String remark;

    @ApiModelProperty(name = "riskMeasure" , value = "整改措施")
    private String riskMeasure;

    @ApiModelProperty(name = "czbz" , value = "操作标志（i：新增(默认)，u：更新，d：删除）")
    private String czbz;

    @NotNull(message = "最后更新时间必填")
    @ApiModelProperty(name = "zhgxsj" , value = "最后更新时间")
    private LocalDateTime zhgxsj;
}
