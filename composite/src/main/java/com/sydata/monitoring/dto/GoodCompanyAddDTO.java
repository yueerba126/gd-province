package com.sydata.monitoring.dto;

import com.sydata.monitoring.entity.GoodCompany;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 流通检测-好粮油企业新增DTO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="流通检测-好粮油企业新增参数", description="流通检测-好粮油企业新增参数")
public class GoodCompanyAddDTO extends GoodCompany {


}
