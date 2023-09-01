package com.sydata.basis.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
import java.time.LocalDateTime;

/**
 * 库区图仓房点位标注对象 basis_cargo_label
 *
 * @author lzq
 * @date 2022-10-11
 */
@ApiModel(description = "库区图仓房点位标注")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("basis_cargo_label")
public class CargoLabel extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键ID(货位代码)")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "货位相对位置")
    private String hwxdwz;

    @ApiModelProperty(value = "货位位置样式")
    private String hwwzys;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}