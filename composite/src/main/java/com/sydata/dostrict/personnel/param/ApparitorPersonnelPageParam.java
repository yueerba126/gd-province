package com.sydata.dostrict.personnel.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author fuql
 * @describe 行政管理模块--企业人员分页参数
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "行政管理模块--企业人员分页参数")
public class ApparitorPersonnelPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键id(单位代码+身份证号码)")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty("单位名称")
    private String dwmc;

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty("姓名")
    private String xm;

    @ApiModelProperty("身份证号码")
    private String sfzhm;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;

    @ApiModelProperty("入职日期")
    private LocalDate beginRzrq;

    @ApiModelProperty("入职日期")
    private LocalDate endRzrq;

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("在岗状态")
    private String zgzt;

    @ApiModelProperty("政治面貌")
    private String zzmm;

    @ApiModelProperty("岗位性质")
    private String gwxz;

    @ApiModelProperty("人员类别")
    private String rylb;
}
