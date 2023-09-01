package com.sydata.filing.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.constraints.Size;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* @Description:TODO(仓储备案-仓储库点-分页参数)
* @date 2023年06月16日
* @version: V1.0
* @author: lzq
*
*/
@ApiModel(value="WarehousingFilingStockPageParam对象", description="仓储备案-仓储库点-分页参数")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=true)
public class WarehousingFilingStockPageParam extends PageQueryParam implements Serializable {

private static final long serialVersionUID = 1687254203072L;

    @ApiModelProperty(name = "id" , value = "主键ID" , example = "主键ID")
    @Size(min = 0, max = 66, message = "主键ID 长度必须在 0 - 66 之间")
    private String id;

    @ApiModelProperty(name = "companyId" , value = "仓储企业ID" , example = "仓储企业ID")
    @Size(min = 0, max = 66, message = "仓储企业ID 长度必须在 0 - 66 之间")
    private String companyId;

    @ApiModelProperty(name = "dwdm" , value = "仓储单位统一社会信用代码" , example = "仓储单位统一社会信用代码")
    @Size(min = 0, max = 18, message = "仓储单位统一社会信用代码 长度必须在 0 - 18 之间")
    private String dwdm;

    @ApiModelProperty(name = "dwmc" , value = "粮油仓储单位名称" , example = "粮油仓储单位名称")
    @Size(min = 0, max = 200, message = "粮油仓储单位名称 长度必须在 0 - 200 之间")
    private String dwmc;

    @ApiModelProperty(name = "kddm" , value = "库点编号" , example = "库点编号")
    @Size(min = 0, max = 21, message = "库点编号 长度必须在 0 - 21 之间")
    private String kddm;

    @ApiModelProperty(name = "kdmc" , value = "库点名称" , example = "库点名称")
    @Size(min = 0, max = 256, message = "库点名称 长度必须在 0 - 256 之间")
    private String kdmc;

    @ApiModelProperty(name = "ids" ,value = "id集合")
    private List<String> ids;

}
