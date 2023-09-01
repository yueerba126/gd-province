package com.sydata.report.api.param.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.common.domain.BaseFiledEntity;
import com.sydata.report.api.param.BaseReportParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @description 入库检验信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "入库检验信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InStockCheckReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "入库检验单号")
    private String rkjydh;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "入库业务单号")
    private String rkywdh;

    @ApiModelProperty(value = "扦样时间:格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime qysj;

    @ApiModelProperty(value = "扦样人姓名")
    private String qyrxm;

    @ApiModelProperty(value = "扦样方式:0：人工； 1： 自动； 2：智能随机")
    private String qyfs;

    @ApiModelProperty(value = "检验项目")
    private String jyxm;

    @ApiModelProperty(value = "检验值")
    private String jyz;

    @ApiModelProperty(value = "增扣价")
    private String zkj;

    @ApiModelProperty(value = "增扣量")
    private String zkl;

    @ApiModelProperty(value = "检验人姓名")
    private String jyrxm;

    @ApiModelProperty(value = "检验时间 格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime jysj;

    @ApiModelProperty(value = "检验结果:0：不合格  1：合格")
    private String jyjg;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食定等")
    private String lsdd;

    @ApiModelProperty(value = "保管员复核:0：不合格 1：合格")
    private String bgyfh;
}
