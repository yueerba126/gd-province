package com.sydata.common.file.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.file.domain.FileStorage;
import com.sydata.common.organize.annotation.DataBindOrganize;
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
 * @description 文件存储VO
 * @date 2022/10/24 14:56
 */
@ApiModel(description = "文件存储VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FileStorageVo extends FileStorage implements Serializable {

    @DataBindOrganize(sourceField = "#enterpriseId")
    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "组织名称")
    private String enterpriseName;
}
