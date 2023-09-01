package com.sydata.reserve.layout.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;
import static com.baomidou.mybatisplus.annotation.IdType.INPUT;

/**
 * 储备布局地理信息-质检机构
 *
 * @author lzq
 * @date 2022-07-08
 */
@ApiModel(description = "储备布局地理信息-质检机构")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("reserve_layout_testing_institutes")
public class TestingInstitutes extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键ID（货位代码）廒间代码+2位顺序码")
    @TableId(value = "id", type = AUTO)
    private String id;

    @ApiModelProperty("质检机构名称")
    private String zjjgmc;

    @ApiModelProperty("单位联系人")
    private String dwlxr;

    @ApiModelProperty("联系电话")
    private String lxdh;

    @ApiModelProperty("是否启用，0：启用，1：未启用")
    private String sfqy;

    @ApiModelProperty("单位地址")
    private String dwdz;

    @ApiModelProperty("备注")
    private String bz;

}