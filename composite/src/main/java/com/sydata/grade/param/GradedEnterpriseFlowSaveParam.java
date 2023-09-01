/**
 * @filename:GradedEnterpriseFlowSaveParam 2023年05月24日
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
import java.util.Date;

/**   
 * @Description:TODO(等级粮库评定管理-企业申报流程表-保存参数)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="GradedEnterpriseFlowSaveParam对象", description="等级粮库评定管理-企业申报流程表-保存参数")
@Data
@ToString
@Accessors(chain = true)
public class GradedEnterpriseFlowSaveParam implements Serializable {

	private static final long serialVersionUID = 1684892830417L;

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    @ApiModelProperty(name = "id" , value = "主键ID")
    private String id;
	@ApiModelProperty(name = "createBy" , value = "创建者")
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "创建时间")
    private LocalDateTime createTime;
	@ApiModelProperty(name = "czbz" , value = "操作标志")
    private String czbz;
	@ApiModelProperty(name = "flowCode" , value = "流程编码")
    private String flowCode;
	@ApiModelProperty(name = "flowSort" , value = "流程排序")
    private Integer flowSort;
	@ApiModelProperty(name = "flowStatus" , value = "流程状态(1:通过，2:未通过)")
    private String flowStatus;
	@ApiModelProperty(name = "qyid" , value = "企业ID")
    private String qyid;
    @ApiModelProperty(name = "xzqhdm", value = "所在区域代码")
    private String xzqhdm;
	@ApiModelProperty(name = "updateBy" , value = "更新者")
    private String updateBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime" , value = "修改时间")
    private LocalDateTime updateTime;
}
