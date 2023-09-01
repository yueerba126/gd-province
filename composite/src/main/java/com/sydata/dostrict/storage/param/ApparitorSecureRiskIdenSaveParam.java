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

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author lzq
 * @date 2022-04-26
 * @description 安全仓储-风险智能识别-新增参数
 * @version: 1.0
 */
@ApiModel(description = "安全仓储-风险智能识别-新增参数")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorSecureRiskIdenSaveParam implements Serializable {

    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "安全生产-风险智能识别Id")
    private String id;

    @ApiModelProperty(name = "dwdm" , value = "企业")
    private String dwdm;

    @ApiModelProperty(name = "kqdm" , value = "库点")
    private String kqdm;

    @ApiModelProperty(name = "remark" , value = "线索描述")
    private String remark;

    @ApiModelProperty(name = "riskType" , value = "风险类型")
    private String riskType;

    @ApiModelProperty(name = "yjlxType" , value = "预警类型")
    private String yjlxType;

    @ApiModelProperty(name = "czbz" , value = "操作标志（i：新增(默认)，u：更新，d：删除）")
    private String czbz;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(name = "yjrq" , value = "预警日期")
    private LocalDate yjrq;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "zhgxsj" , value = "最后更新时间")
    private LocalDateTime zhgxsj;
}
