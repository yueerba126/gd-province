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
 * 基础信息-货位信息对象 basis_location
 *
 * @author lzq
 * @date 2022-07-08
 */
@ApiModel(description = "基础信息-货位信息")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("basis_cargo")
public class Cargo extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键ID（货位代码）廒间代码+2位顺序码")
    @TableId(value = "id", type = INPUT)
    private String id;

    @ApiModelProperty("货位代码")
    private String hwdm;

    @ApiModelProperty("货位名称")
    private String hwmc;

    @ApiModelProperty("廒间代码")
    private String ajdm;

    @ApiModelProperty("货位启用日期")
    private LocalDate hwqyrq;

    @ApiModelProperty("货位容量")
    private BigDecimal hwrl;

    @ApiModelProperty("保管单位")
    private String bgdw;

    @ApiModelProperty("保管员")
    private String bgy;

    @ApiModelProperty("操作标志")
    private String czbz;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime zhgxsj;
}