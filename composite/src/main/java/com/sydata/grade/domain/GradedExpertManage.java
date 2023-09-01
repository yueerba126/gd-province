/**
 * @filename:GradedExpertManage 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
import cn.afterturn.easypoi.excel.annotation.Excel;
import java.util.Date;
import java.util.List;

/**   
 * @Description:TODO(等级粮库评定管理-专家管理实体类)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="GradedExpertManage对象", description="等级粮库评定管理-专家管理")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("graded_expert_manage")
public class GradedExpertManage implements Serializable {

	private static final long serialVersionUID = 1685005952447L;

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    @ApiModelProperty(name = "id" , value = "主键ID")
    private String id;
    @Excel(name = "操作标志")
    @ApiModelProperty(name = "czbz" , value = "操作标志")
    private String czbz;
    @Excel(name = "姓名")
    @ApiModelProperty(name = "expertName" , value = "姓名")
    private String expertName;
    @Excel(name = "性别(1男2女)")
    @ApiModelProperty(name = "expertSex" , value = "性别(1男2女)")
    private String expertSex;
    @Excel(name = "排序")
    @ApiModelProperty(name = "expertSort" , value = "排序")
    private Integer expertSort;
    @Excel(name = "职称")
    @ApiModelProperty(name = "expertTitle" , value = "职称")
    private String expertTitle;
    @TableField(exist = false)
    @ApiModelProperty(name = "expertTitles" , value = "职称集合")
    private String expertTitles;
    @Excel(name = "工作单位")
    @ApiModelProperty(name = "gzdw" , value = "工作单位")
    private String gzdw;
    @Excel(name = "开户行支行名称")
    @ApiModelProperty(name = "khzh" , value = "开户行支行名称")
    private String khzh;
    @Excel(name = "参与评定年限")
    @ApiModelProperty(name = "pdnx" , value = "参与评定年限")
    private String pdnx;
    @TableField(exist = false)
    @ApiModelProperty(name = "pdnxs" , value = "参与评定年限集合")
    private String pdnxs;
    @Excel(name = "手机号")
    @ApiModelProperty(name = "phoneNum" , value = "手机号")
    private String phoneNum;
    @Excel(name = "身份证号")
    @ApiModelProperty(name = "sfzh" , value = "身份证号")
    private String sfzh;
    @Excel(name = "银行卡号")
    @ApiModelProperty(name = "yhkh" , value = "银行卡号")
    private String yhkh;
    @ApiModelProperty(name = "createBy", value = "创建者")
    private String createBy;
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(name = "updateBy", value = "更新者")
    private String updateBy;
    @ApiModelProperty(name = "updateTime", value = "修改时间")
    private LocalDateTime updateTime;
    @TableField(exist = false)
    @ApiModelProperty(name = "gradedMergeExpertManages", value = "合并信息")
    private List<GradedMergeExpertManage> gradedMergeExpertManages;
    @TableField(exist = false)
    @ApiModelProperty(name = "ids", value = "ids")
    private List<String> ids;
}
