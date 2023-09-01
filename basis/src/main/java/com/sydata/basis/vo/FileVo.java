package com.sydata.basis.vo;

import com.sydata.basis.domain.File;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
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
 * @describe 基础信息-文件信息VO
 * @date 2022-07-09 16:38
 */
@ApiModel(description = "基础信息-文件信息VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FileVo extends File implements Serializable {

    @DataBindStockHouse
    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindDict(sourceField = "#wjlx", sourceFieldCombination = "wjlx")
    @ApiModelProperty(value = "文件类型名称")
    private String wjlxName;
}
