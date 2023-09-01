package com.sydata.filing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.filing.domain.WarehousingFilingAttachment;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import com.sydata.filing.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;

/**   
 * @Description:TODO(仓储备案-仓储附件Vo)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="WarehousingFilingAttachmentVo对象", description="仓储备案-仓储附件Vo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class WarehousingFilingAttachmentVo extends WarehousingFilingAttachment implements Serializable {

	private static final long serialVersionUID = 1687660852365L;

    @DataBindWarehousingFilingCompany(sourceField = "#companyId")
    @ApiModelProperty(name = "companyIdName" , value = "仓储企业ID")
    private String companyIdName;

}
