package com.sydata.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 温湿度监测信息表
 *
 * @author lzq
 * @date 2022/8/19 10:52
 */
@ApiModel(value = "com-sydata-manage-domain-GrainMonitor")
@Data
@TableName(value = "manage_grain_monitor")
public class GrainMonitor extends BaseFiledEntity implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键id-温湿度检测单号")
    private String id;

    @ApiModelProperty(value = "温湿度检测单号:货位代码+检测日期（yyyyMMdd）+4位顺序号组成")
    private String wsdjcdh;

    @ApiModelProperty(value = "检测时间")
    private LocalDateTime jcsj;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "仓房外温")
    private BigDecimal cfww;

    @ApiModelProperty(value = "仓房外湿")
    private BigDecimal cfws;

    @ApiModelProperty(value = "仓房内温")
    private BigDecimal cfnw;

    @ApiModelProperty(value = "仓房内湿")
    private BigDecimal cfns;

    @ApiModelProperty(value = "粮食最高温")
    private BigDecimal lszgw;

    @ApiModelProperty(value = "粮食最低温")
    private BigDecimal lszdw;

    @ApiModelProperty(value = "粮食平均温")
    private BigDecimal lspjw;

    @ApiModelProperty(value = "粮食温度值集合")
    private String lswdzjh;

    @ApiModelProperty(value = "粮食湿度值集合")
    private String lssdzjh;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;

}