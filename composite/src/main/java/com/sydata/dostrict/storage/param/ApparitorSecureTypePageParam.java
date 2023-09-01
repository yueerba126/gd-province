package com.sydata.dostrict.storage.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @date 2022-04-26
 * @description 安全仓储-安全生产-制度类别-分页参数
 * @version: 1.0
 */
@ApiModel(description = "安全仓储-安全生产-制度类别-分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ApparitorSecureTypePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(name = "typeName" , value = "分类名称")
    private String typeName;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

}
