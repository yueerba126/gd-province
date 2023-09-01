package com.sydata.manage.vo;

import com.sydata.common.basis.annotation.*;
import com.sydata.manage.domain.GasInformation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 气体信息表
 * </p>
 *
 * @author lzq
 * @since 2022-05-06
 */
@Data
public class GasInformationVo extends GasInformation implements Serializable {


    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindCargo
    @ApiModelProperty(value = "货位名称")
    private String hwmc;

    @ApiModelProperty(value = "作业类型名称")
    @DataBindDict(sourceField = "#zylx", sourceFieldCombination = "zylx")
    private String zylxName;

}
