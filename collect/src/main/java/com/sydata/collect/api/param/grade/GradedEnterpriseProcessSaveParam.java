/**
 * @filename:GradedEnterpriseProcessSaveParam 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.collect.api.param.grade;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description:TODO(等级粮库评定管理-企业申报审核详情-保存参数)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedEnterpriseProcessSaveParam对象", description = "等级粮库评定管理-企业申报审核详情-保存参数")
@Data
@ToString
@Accessors(chain = true)
public class GradedEnterpriseProcessSaveParam implements Serializable {

    private static final long serialVersionUID = 1684748190668L;

    @ApiModelProperty(name = "id", value = "主键ID")
    private String id;
    @ApiModelProperty(name = "createBy", value = "创建者")
    private String createBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(name = "czbz", value = "操作标志")
    private String czbz;
    @ApiModelProperty(name = "qyid", value = "企业ID")
    private String qyid;
    @ApiModelProperty(name = "shjg", value = "审核结果")
    private String shjg;
    @ApiModelProperty(name = "qyshr", value = "审核人")
    private String qyshr;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name = "shsj", value = "审核时间")
    private LocalDateTime shsj;
    @ApiModelProperty(name = "shyj", value = "审核意见")
    private String shyj;
    @ApiModelProperty(name = "updateBy", value = "更新者")
    private String updateBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name = "updateTime", value = "修改时间")
    private LocalDateTime updateTime;
    @ApiModelProperty(name = "enterpriseId" ,value = "企业ID")
    private Long enterpriseId;
    @ApiModelProperty(name = "warehouseId" ,value = "所属库点ID")
    private String warehouseId;
}
