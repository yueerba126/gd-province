package com.sydata.dostrict.storage.param;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.sydata.dostrict.storage.domain.ApparitorSecureType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @date 2022-04-26
 * @description 安全仓储-安全生产-制度类别-查询参数
 * @version: 1.0
 */
@ApiModel(description = "安全仓储-安全生产-制度类别-查询参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ApparitorSecureTypeSelectParam extends ApparitorSecureType implements Serializable {

    @ApiModelProperty(value = "是否显示数量")
    private Integer isShowNum;

    @ApiModelProperty(value = "是否显示（无）分类")
    private Integer isShowHave;

}
