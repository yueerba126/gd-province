package com.sydata.basis.domain;

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
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.IdType.INPUT;

/**
 * 基础信息-库区信息对象 basis_stock_house
 *
 * @author lzq
 * @date 2022-07-08
 */
@ApiModel(description = "基础信息-库区信息")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("basis_stock_house")
public class StockHouse extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键ID（库区代码）企业信用代码+3位顺序码")
    @TableId(value = "id", type = INPUT)
    private String id;

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @ApiModelProperty(value = "库区地址")
    private String kqdz;

    @ApiModelProperty("行政区划代码")
    private String xzqhdm;

    @ApiModelProperty(value = "库区产权")
    private String kqcq;

    @ApiModelProperty(value = "有效仓容（单位：吨）")
    private BigDecimal yxcr;

    @ApiModelProperty(value = "有效罐容（单位：吨）")
    private BigDecimal yxgr;

    @ApiModelProperty(value = "占地面积（指库区土地面积，单位：平方米）")
    private BigDecimal zdmj;

    @ApiModelProperty(value = "仓房数")
    private Integer cfs;

    @ApiModelProperty(value = "油罐数")
    private Integer ygs;

    @ApiModelProperty(value = "库区经度")
    private BigDecimal jd;

    @ApiModelProperty(value = "库区纬度")
    private BigDecimal wd;

    @ApiModelProperty("操作标志")
    private String czbz;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime zhgxsj;

    @ApiModelProperty(value = "海康区域ID")
    private String hkRegionId;
}