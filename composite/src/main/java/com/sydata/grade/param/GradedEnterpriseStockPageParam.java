/**
 * @filename:GradedEnterpriseStockPageParam 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.param;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
@EqualsAndHashCode(callSuper=true)
public class GradedEnterpriseStockPageParam extends PageQueryParam implements Serializable {

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
    @ApiModelProperty(name = "spzt" , value = "授牌状态")
    private String spzt;
    @ApiModelProperty(name = "spdj" , value = "授牌等级(1A-5A)")
    private String spdj;
    @ApiModelProperty(name = "sbzt", value = "申报状态")
    private String sbzt;
    @ApiModelProperty(name = "sbdj", value = "申报等级(1A-5A)")
    private String sbdj;
    @ApiModelProperty(name = "sbnfList", value = "申报年份集合")
    private List<String> sbnfList;
    @ApiModelProperty(name = "czlx", value = "操作类型（降级 1，摘除 2）")
    private String czzt;
    @ApiModelProperty(name = "ids", value = "ids")
    private List<String> ids;
}
