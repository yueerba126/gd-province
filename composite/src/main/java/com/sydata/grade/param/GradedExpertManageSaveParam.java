/**
 * @filename:GradedExpertManageSaveParam 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.param;
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
 * @Description:TODO(等级粮库评定管理-专家管理-保存参数)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="GradedExpertManageSaveParam对象", description="等级粮库评定管理-专家管理-保存参数")
@Data
@ToString
@Accessors(chain = true)
public class GradedExpertManageSaveParam implements Serializable {

	private static final long serialVersionUID = 1685005952447L;

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    @ApiModelProperty(name = "id" , value = "主键ID")
    private String id;
    @ApiModelProperty(name = "czbz" , value = "操作标志")
    private String czbz;
    @ApiModelProperty(name = "expertName" , value = "姓名")
    private String expertName;
    @ApiModelProperty(name = "expertSex" , value = "性别(1男2女)")
    private String expertSex;
    @ApiModelProperty(name = "expertSort" , value = "排序")
    private Integer expertSort;
    @ApiModelProperty(name = "expertTitle" , value = "职称")
    private String expertTitle;
    @ApiModelProperty(name = "gzdw" , value = "工作单位")
    private String gzdw;
    @ApiModelProperty(name = "khzh" , value = "开户行支行名称")
    private String khzh;
    @ApiModelProperty(name = "pdnx" , value = "参与评定年限")
    private String pdnx;
    @ApiModelProperty(name = "phoneNum" , value = "手机号")
    private String phoneNum;
    @ApiModelProperty(name = "sfzh" , value = "身份证号")
    private String sfzh;
    @ApiModelProperty(name = "yhkh" , value = "银行卡号")
    private String yhkh;

    @ApiModelProperty(name = "createBy" , value = "创建者")
    private String createBy;
    @ApiModelProperty(name = "createTime" , value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(name = "updateBy" , value = "更新者")
    private String updateBy;
    @ApiModelProperty(name = "updateTime" , value = "修改时间")
    private LocalDateTime updateTime;
}
