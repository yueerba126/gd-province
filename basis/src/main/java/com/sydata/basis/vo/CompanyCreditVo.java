package com.sydata.basis.vo;

import com.sydata.basis.domain.CompanyCredit;
import com.sydata.common.basis.annotation.DataBindCompany;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 企业信用信息VO
 * @date 2022/10/11 17:53
 */
@ApiModel(description = "企业信用信息VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CompanyCreditVo extends CompanyCredit implements Serializable {

    @DataBindCompany
    @ApiModelProperty(value = "单位名称")
    private String dwmc;
}
