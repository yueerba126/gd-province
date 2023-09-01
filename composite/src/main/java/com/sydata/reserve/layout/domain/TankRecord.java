package com.sydata.reserve.layout.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.IdType.INPUT;

/**
 * 储备布局地理信息-油罐信息备案
 *
 * @author lzq
 * @date 2022-07-08
 */
@ApiModel(description = "储备布局地理信息-油罐信息备案")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("reserve_layout_tank")
public class TankRecord extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键ID（油罐代码）库区代码+4位顺序码")
    @TableId(value = "id", type = INPUT)
    private String id;

    @ApiModelProperty("油罐代码")
    private String ygdm;

    @ApiModelProperty("油罐名称")
    private String ygmc;

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("罐容")
    private BigDecimal gr;

    @ApiModelProperty("建造时间")
    private LocalDate jzsj;

    @ApiModelProperty("油罐及附属设施是否完好")
    private String ygjfssssfwh;

    @ApiModelProperty("有无加热装置")
    private String ywjrzz;

    @ApiModelProperty("油罐类型")
    private String yglx;

    @ApiModelProperty("罐内直径")
    private BigDecimal gnzj;

    @ApiModelProperty("罐内高度")
    private BigDecimal gngd;

    @ApiModelProperty("检定方式")
    private String jdfs;

    @ApiModelProperty("焊接方式")
    private String hjfs;

    @ApiModelProperty("油罐状态")
    private String ygzt;

    @ApiModelProperty("设计单位")
    private String sjdw;

    @ApiModelProperty("建设单位")
    private String jsdw;

    @ApiModelProperty("监理单位")
    private String jldw;

    @ApiModelProperty("状态 0：保存，1：提交，2：审核")
    private String status;

    @ApiModelProperty("单位代码")
    private String dwdm;
}