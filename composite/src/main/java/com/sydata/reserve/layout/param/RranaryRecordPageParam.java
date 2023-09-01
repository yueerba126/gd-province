package com.sydata.reserve.layout.param;

import com.sydata.common.param.PageQueryParam;
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
 * @describe 储备布局地理信息-廒间信息备案分页参数
 * @date 2022-07-09 16:11
 */
@ApiModel(description = "储备布局地理信息-廒间信息备案分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RranaryRecordPageParam extends PageQueryParam implements Serializable {


    @ApiModelProperty(value = "企业id")
    private String dwdm;


    @ApiModelProperty("仓房代码")
    private String cfdm;

    @ApiModelProperty(value = "库区id")
    private String kqdm;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("廒间id")
    private String id;

    @ApiModelProperty("廒间名称")
    private String ajmc;


}
