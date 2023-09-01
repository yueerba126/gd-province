package com.sydata.monitoring.dto;

import com.sydata.monitoring.entity.GoodProduceCompany;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 流通检测-放心粮油企业 编辑DTO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="流通检测-放心粮油企业编辑参数", description="流通检测-放心粮油企业编辑参数")
public class GoodProduceCompanyEditDTO extends GoodProduceCompany {


}
