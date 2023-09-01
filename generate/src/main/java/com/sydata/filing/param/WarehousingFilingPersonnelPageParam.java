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
* @Description:TODO(仓储备案-仓储人员-分页参数)
* @date 2023年06月16日
* @version: V1.0
* @author: lzq
*
*/
@ApiModel(value="WarehousingFilingPersonnelPageParam对象", description="仓储备案-仓储人员-分页参数")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=true)
public class WarehousingFilingPersonnelPageParam extends PageQueryParam implements Serializable {

private static final long serialVersionUID = 1687660852365L;

    @ApiModelProperty(name = "id" , value = "主键ID" , example = "主键ID")
    @Size(min = 0, max = 66, message = "主键ID 长度必须在 0 - 66 之间")
    private String id;

    @ApiModelProperty(name = "companyId" , value = "仓储企业ID" , example = "1")
    @Size(min = 0, max = 66, message = "仓储企业ID 长度必须在 0 - 66 之间")
    private String companyId;

    @ApiModelProperty(name = "xm" , value = "姓名" , example = "姓名")
    @Size(min = 0, max = 32, message = "姓名 长度必须在 0 - 32 之间")
    private String xm;

    @ApiModelProperty(name = "cyzw" , value = "从业人员职务/岗位(0:粮油保管员 1:粮油质检员）" , example = "1")
    @Size(min = 0, max = 1, message = "从业人员职务/岗位(0:粮油保管员 1:粮油质检员） 长度必须在 0 - 1 之间")
    private String cyzw;

    @ApiModelProperty(name = "rylb" , value = "人员类别(0:法人,1:主要联系人,2:从业人员)" , example = "1")
    @Size(min = 0, max = 1, message = "人员类别(0:法人,1:主要联系人,2:从业人员) 长度必须在 0 - 1 之间")
    private String rylb;

    @ApiModelProperty(name = "ryjb" , value = "级别(0:初级工 1:中级工 2:高级工)" , example = "1")
    @Size(min = 0, max = 1, message = "级别(0:初级工 1:中级工 2:高级工) 长度必须在 0 - 1 之间")
    private String ryjb;

    @ApiModelProperty(name = "ids" ,value = "id集合")
    private List<String> ids;

}
