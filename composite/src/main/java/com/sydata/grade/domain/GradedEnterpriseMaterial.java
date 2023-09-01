/**
 * @filename:GradedEnterpriseMaterial 2023年05月22日
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
 * @Description:TODO(等级粮库评定管理-企业申报证明材料实体类)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedEnterpriseMaterial对象", description = "等级粮库评定管理-企业申报证明材料")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("graded_enterprise_material")
public class GradedEnterpriseMaterial implements Serializable {

    private static final long serialVersionUID = 1684747364536L;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id", value = "主键ID")
    private String id;
    @ApiModelProperty(name = "clSort", value = "材料顺序")
    private Integer clSort;
    @ApiModelProperty(name = "czbz", value = "操作标志")
    private String czbz;
    @ApiModelProperty(name = "fileId", value = "附件id")
    private String fileId;
    @ApiModelProperty(name = "fileName", value = "材料名称")
    private String fileName;
    @ApiModelProperty(name = "qyid", value = "企业ID")
    private String qyid;

    @ApiModelProperty(name = "createBy", value = "创建者")
    private String createBy;
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(name = "updateBy", value = "更新者")
    private String updateBy;
    @ApiModelProperty(name = "updateTime", value = "修改时间")
    private LocalDateTime updateTime;
}
