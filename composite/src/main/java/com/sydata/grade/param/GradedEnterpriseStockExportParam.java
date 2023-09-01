/**
 * @filename:GradedEnterpriseStockPageParam 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**   
 * @Description:TODO(等级粮库评定管理-企业等级库点-分页参数)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="GradedEnterpriseStockPageParam对象", description="等级粮库评定管理-企业等级库点-分页参数")
@Data
@ToString
@Accessors(chain = true)
public class GradedEnterpriseStockExportParam implements Serializable {

	private static final long serialVersionUID = 1684995741089L;

    @ApiModelProperty(name = "id" , value = "id")
    private String id;
    @ApiModelProperty(name = "kqmc" , value = "库点名称")
    private String kqmc;
    @ApiModelProperty(name = "qymc" , value = "企业名称")
    private String qymc;
    @ApiModelProperty(name = "xzqhdm" , value = "所在区域")
    private String xzqhdm;
    @ApiModelProperty(name = "spnf" , value = "授牌年份")
    private String spnf;
}
