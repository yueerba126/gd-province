package com.sydata.safe.asess.vo;

import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.organize.annotation.DataBindDept;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.safe.asess.domain.Template;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static com.sydata.framework.databind.handle.value.bind.ValueBindStrategy.SEPARATED;

/**
 * @author lzq
 * @description 粮食安全考核-考核模板VO
 * @date 2023/2/17 17:48
 */
@ApiModel(description = "粮食安全考核-考核模板VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TemplateVo extends Template implements Serializable {

    @DataBindOrganize
    @ApiModelProperty(value = "组织名称")
    private String organizeName;

    @DataBindOrganize(sourceField = "#autoAllotDeptIds", valueBindStrategy = SEPARATED)
    @ApiModelProperty(value = "自动分配部门名称S")
    private String autoAllotDeptNames;

    @DataBindOrganize(sourceField = "#regionIds", valueBindStrategy = SEPARATED)
    @ApiModelProperty(value = "行政区域名称S")
    private String regionNames;
}
