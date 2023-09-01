package com.sydata.dostrict.personnel.param;

import com.sydata.dostrict.personnel.domain.ApparitorSystem;
import com.sydata.dostrict.personnel.domain.ApparitorSystemZoning;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * 行政管理-人员制度管理新增参数
 *
 * @author fuql
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "行政管理-人员制度管理新增参数")
public class ApparitorSystemSaveParam extends ApparitorSystem implements Serializable {

    @ApiModelProperty(value = "人员制度行政区划数据")
    @Valid
    private List<ApparitorSystemZoning> zoningList;
}
