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
import java.time.LocalDateTime;

/**
* @Description:TODO(仓储备案-仓储设备-分页参数)
* @date 2023年06月16日
* @version: V1.0
* @author: lzq
*
*/
@ApiModel(value="WarehousingFilingDevicePageParam对象", description="仓储备案-仓储设备-分页参数")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=true)
public class WarehousingFilingDevicePageParam extends PageQueryParam implements Serializable {

private static final long serialVersionUID = 1687660852365L;

    @ApiModelProperty(name = "id" , value = "主键ID" , example = "主键ID")
    @Size(min = 0, max = 66, message = "主键ID 长度必须在 0 - 66 之间")
    private String id;

    @ApiModelProperty(name = "companyId" , value = "仓储企业ID" , example = "1")
    @Size(min = 0, max = 66, message = "仓储企业ID 长度必须在 0 - 66 之间")
    private String companyId;

    @ApiModelProperty(name = "stockId" , value = "仓储库点ID" , example = "1")
    @Size(min = 0, max = 66, message = "仓储库点ID 长度必须在 0 - 66 之间")
    private String stockId;

    @ApiModelProperty(name = "sbmc" , value = "设备名称" , example = "设备名称")
    @Size(min = 0, max = 20, message = "设备名称 长度必须在 0 - 20 之间")
    private String sbmc;

    @ApiModelProperty(name = "ids" ,value = "id集合")
    private List<String> ids;

}
