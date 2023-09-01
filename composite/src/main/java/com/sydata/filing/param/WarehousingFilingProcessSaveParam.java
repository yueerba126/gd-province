/**
 * @filename:WarehousingFilingProcessSaveParam 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.filing.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description:TODO(审核-审核参数)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "WarehousingFilingProcessSaveParam对象", description = "审核-审核参数")
@Data
@ToString
@Accessors(chain = true)
public class WarehousingFilingProcessSaveParam implements Serializable {

    private static final long serialVersionUID = 1684748190668L;

    @ApiModelProperty(name = "companyId" , value = "仓储企业ID")
    private String companyId;
    @ApiModelProperty(name = "shjg", value = "审核结果")
    private String shjg;
    @ApiModelProperty(name = "shyj", value = "审核意见")
    private String shyj;
}
