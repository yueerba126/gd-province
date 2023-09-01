package com.sydata.dostrict.personnel.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 行政管理-人员制度管理查询参数
 *
 * @author fuql
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "行政管理-人员制度管理查询参数")
public class ApparitorSystemPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "人员制度管理id列表")
    private List<Long> systemIds;

    @ApiModelProperty(value = "部门")
    private String deptId;

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty("单位名称")
    private String dwmc;

    @ApiModelProperty(value = "文件类别id")
    private Long fileTypeId;

    @ApiModelProperty(value = "文件标题")
    private String fileName;

    @ApiModelProperty(value = "发文号")
    private String number;

    @ApiModelProperty(value = "发布人")
    private String releaseId;

    @ApiModelProperty(value = "状态")
    private String billStatus;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime startReleaseTime;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime endReleaseTime;

    @ApiModelProperty(value = "行政区划Id")
    private String regionId;

    @TableField(exist = false)
    @ApiModelProperty(value = "主表行政区划Id")
    private String mainRegionId;
}
