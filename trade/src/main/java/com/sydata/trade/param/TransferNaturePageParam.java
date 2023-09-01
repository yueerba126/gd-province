package com.sydata.trade.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 损益单信息
 *
 * @author lzq
 * @date 2022/8/19 9:37
 */
@Data
public class TransferNaturePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "性质转变单编号")
    private String lsxzzbdh;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "划转前粮食性质代码")
    private String hzqlsxzdm;

    @ApiModelProperty(value = "划转后粮食性质代码")
    private String hzhlsxzdm;

    @ApiModelProperty(value = "划转日期")
    private LocalDate hzrq;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}