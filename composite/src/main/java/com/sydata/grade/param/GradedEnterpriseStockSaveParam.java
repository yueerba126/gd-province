/**
 * @filename:GradedEnterpriseStockSaveParam 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.param;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.common.basis.annotation.DataBindDict;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Description:TODO(等级粮库评定管理-企业等级库点-保存参数)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="GradedEnterpriseStockSaveParam对象", description="等级粮库评定管理-企业等级库点-保存参数")
@Data
@ToString
@Accessors(chain = true)
public class GradedEnterpriseStockSaveParam implements Serializable {

	private static final long serialVersionUID = 1684995741089L;

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    @ApiModelProperty(name = "id" , value = "主键ID")
    private String id;

    @ApiModelProperty(name = "qyid", value = "审核流程企业ID")
    private String qyid;

    @ApiModelProperty(name = "czbz" , value = "操作标志")
    private String czbz;

    @ApiModelProperty(name = "kqdm" , value = "库区代码")
    private String kqdm;

    @ApiModelProperty(name = "kqmc" , value = "库区名称")
    private String kqmc;

    @ApiModelProperty(name = "lkcr" , value = "粮库仓(罐)容(吨)")
    private BigDecimal lkcr;

    @ApiModelProperty(name = "qydj" , value = "企业等级（县、市、省、区）")
    private String qydj;

    @ApiModelProperty(name = "qydm" , value = "企业代码")
    private String qydm;

    @ApiModelProperty(name = "qymc" , value = "企业名称")
    private String qymc;

    @ApiModelProperty(name = "spdj" , value = "授牌等级(1A-5A)")
    private String spdj;

    @ApiModelProperty(name = "spnf" , value = "授牌年份")
    private String spnf;

    @ApiModelProperty(name = "spzt" , value = "授牌状态")
    private String spzt;

    @ApiModelProperty(name = "xzqhdm" , value = "所在区域代码")
    private String xzqhdm;

    @ApiModelProperty(name = "sbdj", value = "申报等级(1A-5A)")
    private String sbdj;

    @ApiModelProperty(name = "sbnf", value = "申报年份")
    private String sbnf;

    @ApiModelProperty(name = "sbrq", value = "申报或推荐日期")
    private LocalDate sbrq;

    @ApiModelProperty(name = "sbzt", value = "申报状态")
    private String sbzt;

    @ApiModelProperty(name = "zpf", value = "自评分（总）")
    private BigDecimal zpf;

    @ApiModelProperty(name = "citySdpf", value = "市实地评分（总）")
    private BigDecimal citySdpf;

    @ApiModelProperty(name = "citySdzt", value = "市实地评分状态")
    private String citySdzt;

    @ApiModelProperty(name = "provinceSdpf", value = "省实地评分（总）")
    private BigDecimal provinceSdpf;

    @ApiModelProperty(name = "provinceSdzt", value = "省实地评分状态")
    private String provinceSdzt;

    @ApiModelProperty(name = "createBy" , value = "创建者")
    private String createBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name = "createTime" , value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "updateBy" , value = "更新者")
    private String updateBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name = "updateTime" , value = "修改时间")
    private LocalDateTime updateTime;
}
