/**
 * @filename:GradedEnterpriseFlow 2023年05月24日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import com.sydata.common.domain.BaseFiledEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**   
 * @Description:TODO(等级粮库评定管理-企业申报流程表实体类)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="GradedEnterpriseFlow对象", description="等级粮库评定管理-企业申报流程表")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("graded_enterprise_flow")
public class GradedEnterpriseFlow implements Serializable {

	private static final long serialVersionUID = 1684892830417L;

    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "主键ID")
    private String id;
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


    @ApiModelProperty(name = "createBy", value = "创建者")
    private String createBy;
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(name = "updateBy", value = "更新者")
    private String updateBy;
    @ApiModelProperty(name = "updateTime", value = "修改时间")
    private LocalDateTime updateTime;
}
