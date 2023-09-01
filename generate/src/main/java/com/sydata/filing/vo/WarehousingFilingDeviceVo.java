package com.sydata.filing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.filing.domain.WarehousingFilingDevice;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import com.sydata.filing.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;

/**   
 * @Description:TODO(仓储备案-仓储设备Vo)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="WarehousingFilingDeviceVo对象", description="仓储备案-仓储设备Vo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class WarehousingFilingDeviceVo extends WarehousingFilingDevice implements Serializable {

	private static final long serialVersionUID = 1687660852365L;

    @DataBindWarehousingFilingCompany(sourceField = "#companyId")
    @ApiModelProperty(name = "companyIdName" , value = "仓储企业ID")
    private String companyIdName;

    @DataBindWarehousingFilingStock(sourceField = "#stockId")
    @ApiModelProperty(name = "stockIdName" , value = "仓储库点ID")
    private String stockIdName;

}
