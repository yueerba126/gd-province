package com.sydata.manage.vo;

import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.manage.domain.PestInformation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.HASH;
import static com.sydata.framework.databind.handle.value.bind.ValueBindStrategy.SEPARATED;

/**
 * @author ：lixi
 * @date ：Created in 2022/5/6 10:30
 * @description：虫害信息接收对象Param
 * @version: 1.0
 */
@Data
public class PestInformationVo extends PestInformation implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindCargo
    @ApiModelProperty(value = "货位名称")
    private String hwmc;

    @DataBindDict(sourceFieldCombination = "jchcff", sourceField = "#jchcff")
    @ApiModelProperty(value = "检查虫害方法名称")
    private String jchcffName;

    @DataBindDict(sourceField = "#hczl", sourceFieldCombination = "hczl", valueBindStrategy = SEPARATED, bindSeparated = HASH)
    @ApiModelProperty(value = "害虫种类名称")
    private String hczlName;

    @DataBindDict(sourceField = "#cldjpd", sourceFieldCombination = "cldjpd")
    @ApiModelProperty(value = "虫粮等级判定名称")
    private String cldjpdName;
}
