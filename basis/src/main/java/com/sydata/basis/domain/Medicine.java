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
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.IdType.INPUT;

/**
 * 基础信息-药剂信息对象 basis_medicine
 *
 * @author lzq
 * @date 2022-07-08
 */
@ApiModel(description = "基础信息-药剂信息")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("basis_medicine")
public class Medicine extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键ID(药剂编号-库区代码-采购日期)")
    @TableId(value = "id", type = INPUT)
    private String id;

    @ApiModelProperty("药剂编号")
    private String yjbh;

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("采购日期")
    private LocalDate cgrq;

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty("库区名称")
    private String kqmc;

    @ApiModelProperty("药剂名称")
    private String yjmc;

    @ApiModelProperty("包装物")
    private String bzw;

    @ApiModelProperty("型号规格")
    private String ggxh;

    @ApiModelProperty("安全使用说明书")
    private String aqsysms;

    @ApiModelProperty("生产厂家")
    private String sccj;

    @ApiModelProperty("采购来源")
    private String cgly;

    @ApiModelProperty("储存条件")
    private String cctj;

    @ApiModelProperty("储存地点")
    private String ccdd;

    @ApiModelProperty("包装物处理方式")
    private String bzwclfs;

    @ApiModelProperty("残渣处理方式")
    private String czclfs;

    @ApiModelProperty("保质期")
    private String bzq;

    @ApiModelProperty("库存数量")
    private BigDecimal kcsl;

    @ApiModelProperty("库存数量单位")
    private String kcsldw;

    @ApiModelProperty("操作标志")
    private String czbz;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime zhgxsj;
}