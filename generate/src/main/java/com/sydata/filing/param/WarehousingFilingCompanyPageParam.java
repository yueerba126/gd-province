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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
* @Description:TODO(仓储备案-仓储企业-分页参数)
* @date 2023年06月16日
* @version: V1.0
* @author: lzq
*
*/
@ApiModel(value="WarehousingFilingCompanyPageParam对象", description="仓储备案-仓储企业-分页参数")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=true)
public class WarehousingFilingCompanyPageParam extends PageQueryParam implements Serializable {

private static final long serialVersionUID = 1687660852365L;

    @ApiModelProperty(name = "id" , value = "主键ID" , example = "主键ID")
    @Size(min = 0, max = 66, message = "主键ID 长度必须在 0 - 66 之间")
    private String id;

    @ApiModelProperty(name = "dwmc" , value = "粮油仓储单位名称" , example = "粮油仓储单位名称")
    @Size(min = 0, max = 200, message = "粮油仓储单位名称 长度必须在 0 - 200 之间")
    private String dwmc;

    @ApiModelProperty(name = "dwlx" , value = "企业类型(字典表dwlx)" , example = "1")
    @Size(min = 0, max = 2, message = "企业类型(字典表dwlx) 长度必须在 0 - 2 之间")
    private String dwlx;

    @ApiModelProperty(name = "bazt" , value = "备案状态 0:保存 1:待备案 2:已备案 3:审核不通过 4:已注销" , example = "1")
    @Size(min = 0, max = 1, message = "备案状态 0:保存 1:待备案 2:已备案 3:审核不通过 4:已注销 长度必须在 0 - 1 之间")
    private String bazt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @ApiModelProperty(name = "beginBarq" ,value = "开始备案日期" ,example = "2023-07-02")
    private LocalDate beginBarq;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @ApiModelProperty(name = "endBarq" ,value = "结束备案日期" ,example = "2023-06-25")
    private LocalDate endBarq;

    @ApiModelProperty(name = "baly" , value = "备案来源(0:库软件,1:粤商局)" , example = "1")
    @Size(min = 0, max = 1, message = "备案来源(0:库软件,1:粤商局) 长度必须在 0 - 1 之间")
    private String baly;

    @ApiModelProperty(name = "ids" ,value = "id集合")
    private List<String> ids;

}
