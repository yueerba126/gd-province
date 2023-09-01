/**
 * @filename:GradedExpertManage 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @Description:TODO(等级粮库评定管理-专家管理合并对象实体类)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="GradedExpertManage合并对象", description="等级粮库评定管理-专家管理合并对象")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GradedMergeExpertManage implements Serializable {

	private static final long serialVersionUID = 1L;
    @TableField(exist = false)
    @ApiModelProperty(name = "id" , value = "主键ID")
    private String id;
    @TableField(exist = false)
    @ApiModelProperty(name = "expertName" , value = "姓名")
    private String expertName;
    @TableField(exist = false)
    @ApiModelProperty(name = "expertTitle" , value = "职称")
    private String expertTitle;
    @TableField(exist = false)
    @ApiModelProperty(name = "pdnx" , value = "参与评定年限")
    private String pdnx;
}
